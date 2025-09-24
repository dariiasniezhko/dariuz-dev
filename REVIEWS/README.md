# Hotel Review Sentiment & NLP Analysis

Automated text analytics workflow for hotel reviews. Detects sentiment distribution, calculates daily and rolling averages, and extracts most frequent keywords for actionable feedback.

## Project Overview

- Designed to help hotel teams understand guest feedback and reputation from online reviews.
- Processes booking reviews CSV, categorizes by sentiment, visualizes trends, and highlights actionable pros/cons.
- Integrates basic NLP to reveal most frequent words, supporting quality improvements in service and operations.

## Technologies

- Python, pandas, matplotlib, re, collections.Counter
- CSV file input (hotel reviews)
- NLP (tokenization, frequency analysis, sentiment mapping)

## How to use

1. Place the reviews dataset as `booking_like_hotel_reviews.csv` in the project folder.
2. Run the script:
   - Sentiment analysis and distribution (pie chart)
   - Daily average and 7-day rolling ratings (trend plot)
   - NLP for top keywords (bar chart of top 15 words)
3. All results are visualized with matplotlib for quick exploration (no extra libraries required).

## Key Results

- Instant overview of feedback positivity/negativity with a sentiment pie chart.
- Rating trends visualized: daily vs. rolling averages for reputation tracking.
- Top fifteen keywords directly extracted from guest reviews, ready to inform business improvements.

## Author

Dariia Sniezhko
