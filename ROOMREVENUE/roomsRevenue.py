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
csv = os.environ.get("CSV_PATH", "hotel_rooms_revenue_demo.csv")

# ==== LOAD CSV ====
df = pd.read_csv(csv, parse_dates=["stay_date"])
df["rooms_sold"] = df["rooms_sold"].fillna(0).astype(int)
df["rooms_available"] = df["rooms_available"].fillna(0).astype(int)

# ==== SINGLE ENGINE ====
engine = create_engine(f"mysql+pymysql://{user}:{password}@{host}/{dbname}")

# ==== WRITE CSV TO TABLE, NO MANUAL DDL ====
df.to_sql('bookings', engine, if_exists="replace", index=False)
print(f"Inserted {len(df)} rows into bookings.")

# ==== KPI ANALYSIS (SQL) & PLOTS ====
kpi_sql = """
SELECT
    stay_date,
    rooms_sold,
    rooms_available,
    ROUND(rooms_sold/NULLIF(rooms_available,0),3) AS OCC,
    ROUND(revenue_rooms/NULLIF(rooms_sold,0),2)   AS ADR,
    ROUND(revenue_rooms/NULLIF(rooms_available,0),2) AS RevPAR
FROM bookings
ORDER BY stay_date;
"""
df_kpi = pd.read_sql(kpi_sql, engine, parse_dates=["stay_date"])
df_kpi.to_csv("rooms_kpi_daily.csv", index=False, encoding="utf-8")
print("Saved: rooms_kpi_daily.csv")

plt.figure(figsize=(10,6))
plt.plot(df_kpi['stay_date'], df_kpi['RevPAR'], label='RevPAR')
plt.xlabel("Date")
plt.ylabel("RevPAR (€)")
plt.title("Daily RevPAR")
plt.xticks(rotation=45)
plt.legend()
plt.tight_layout()  # for PowerPoint
plt.show()
plt.savefig("plot_revpar_by_date.png")
plt.close()

df_kpi['year'] = df_kpi['stay_date'].dt.isocalendar().year
df_kpi['week'] = df_kpi['stay_date'].dt.isocalendar().week
weekly_revpar = df_kpi.groupby(['year', 'week'], as_index=False)['RevPAR'].mean()
weekly_revpar['week_label'] = weekly_revpar['year'].astype(str) + "-W" + weekly_revpar['week'].astype(str)
plt.figure(figsize=(10,6))
plt.plot(weekly_revpar['week_label'], weekly_revpar['RevPAR'], marker='o', label='Weekly RevPAR')
plt.xlabel("Week")
plt.ylabel("RevPAR (€)")
plt.title("Weekly RevPAR")
plt.xticks(rotation=45)
plt.legend()
plt.tight_layout()  # for PowerPoint
plt.show()
plt.savefig("plot_weekly_revpar.png")
plt.close()
