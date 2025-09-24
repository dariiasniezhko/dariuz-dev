# Hotel Revenue & KPI Automation

Automated pipeline for daily and weekly hotel revenue KPI calculation, visualization, and SQL-based reporting. Converts raw CSV booking export into business metrics and ready-to-use charts for management or presentation.

## Project Overview

- Designed for hotel operations teams to streamline revenue analysis from raw booking data.
- Loads bookings CSV, uploads to MySQL with pandas without manual DDL, calculates core KPIs (Occupancy, ADR, RevPAR) via SQL.
- Generates daily and weekly KPI tables and visualizations (matplotlib PDF/PNG) for use in PowerPoint and business reviews.

## Technologies

- Python, pandas, matplotlib, sqlalchemy, dotenv
- MySQL (pymysql for connection)
- CSV, SQL queries for business logic

## How to use

1. Set environment variables in `.env`: DB_HOST, DB_USER, DB_PASSWORD, DB_NAME, CSV_PATH (optional, defaults to 'hotel_rooms_revenue_demo.csv').
2. Run the script to:
   - Automatically load CSV data and cleanse missing values.
   - Upload to MySQL (no manual table setup needed).
   - Calculate KPIs and save daily results to `rooms_kpi_daily.csv`.
   - Create line plots for daily and weekly RevPAR (`plot_revpar_by_date.png`, `plot_weekly_revpar.png`).

## Key Results

- Rapid export → SQL → analytics workflow, fully automated.
- Clear daily and weekly RevPAR visuals for performance meetings.
- Easily customizable for any hotel or export format.

## Author

Dariia Sniezhko
