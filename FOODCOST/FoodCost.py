import pandas as pd
import matplotlib.pyplot as plt
import os
from dotenv import load_dotenv
from sqlalchemy import create_engine

# ==== ENVIRONMENT SETUP ====
load_dotenv()
host = os.environ.get('DB_HOST')
user = os.environ.get('DB_USER')
password = os.environ.get('DB_PASSWORD')
dbname = os.environ.get('DB_NAME')
excel_path = os.environ.get("FOODCOST_EXCEL", "foodcost_demo.xlsx")

df = pd.read_excel(excel_path, parse_dates=["date"])
df["qty"] = df["qty"].fillna(0).astype(int)
df["unit_cost"] = df["unit_cost"].fillna(0).astype(float)
df["unit_price"] = df["unit_price"].fillna(0).astype(float)
df["amount"] = df["amount"].fillna(0).astype(float)

engine = create_engine(f"mysql+pymysql://{user}:{password}@{host}/{dbname}")

df.to_sql('transactions', engine, if_exists="replace", index=False)
print(f"Inserted {len(df)} rows into transactions.")

# === Query 1: Daily purchases & sales ===
purchaseSalesSQL = """
SELECT 
    date,
    SUM(CASE WHEN doc_type='purchase' THEN amount ELSE 0 END) AS total_purchase,
    SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END) AS total_sales,
    ROUND(SUM(CASE WHEN doc_type='purchase' THEN amount ELSE 0 END) /
          NULLIF(SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END),0) * 100, 2) AS foodcost_pct
FROM transactions
GROUP BY date
ORDER BY date;
"""
df_purchaseSales = pd.read_sql(purchaseSalesSQL, engine, parse_dates=["date"])
df_purchaseSales.to_csv("purchaseSalesDaily.csv", index=False, encoding="utf-8")
print("Saved: purchaseSalesDaily.csv")

# === Query 2: Food cost by category ===
fcCategory = """
SELECT 
    category,
    SUM(CASE WHEN doc_type='purchase' THEN amount ELSE 0 END) AS total_purchase,
    SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END) AS total_sales,
    ROUND(SUM(CASE WHEN doc_type='purchase' THEN amount ELSE 0 END) /
          NULLIF(SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END),0) * 100, 2) AS foodcost_pct
FROM transactions
GROUP BY category
ORDER BY foodcost_pct;
"""
df_fcCategory = pd.read_sql(fcCategory, engine)
df_fcCategory.to_csv("foodcost_by_category.csv", index=False, encoding="utf-8")
print("Saved: foodcost_by_category.csv")

# === Query 3: Margin by category ===
margin = """
SELECT 
    category,
    SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END) -
    SUM(CASE WHEN doc_type='purchase' THEN amount ELSE 0 END) AS margin,
    ROUND(
        (SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END) -
         SUM(CASE WHEN doc_type='purchase' THEN amount ELSE 0 END))
        / NULLIF(SUM(CASE WHEN doc_type='sale' THEN amount ELSE 0 END),0) * 100, 2
    ) AS margin_pct
FROM transactions
GROUP BY category
ORDER BY margin_pct DESC;
"""
df_margin = pd.read_sql(margin, engine)
df_margin.to_csv("category_margin.csv", index=False, encoding="utf-8")
print("Saved: category_margin.csv")

# === Plot 1: Daily purchases/sales ===
plt.figure(figsize=(10,6))
plt.plot(df_purchaseSales['date'], df_purchaseSales['total_purchase'], label='Purchases')
plt.plot(df_purchaseSales['date'], df_purchaseSales['total_sales'], label='Sales')
plt.xlabel("Date")
plt.ylabel("Amount (€)")
plt.title("Purchases and Sales by Date")
plt.xticks(rotation=45)
plt.legend()
plt.tight_layout()  # for PowerPoint
plt.show()
plt.savefig("plot_purchase_sales_by_date.png")
plt.close()

# === Plot 2: Daily food cost % ===
plt.figure(figsize=(10,6))
plt.plot(df_purchaseSales['date'], df_purchaseSales['foodcost_pct'], label='Foodcost %')
plt.xlabel("Date")
plt.ylabel("Foodcost (%)")
plt.title("Food Cost by Date")
plt.xticks(rotation=45)
plt.legend()
plt.tight_layout()  # for PowerPoint
plt.show()
plt.savefig("plot_foodcost_pct_by_date.png")
plt.close()

# === Plot 3: Margin table by category ===
fig, ax = plt.subplots(figsize=(8,4))
ax.axis('off')
ax.table(cellText=df_margin.values, colLabels=df_margin.columns, loc='center')
plt.tight_layout()  # for PowerPoint
plt.show()
plt.savefig("category_margin_table.png")
plt.close()

print("Done.")
