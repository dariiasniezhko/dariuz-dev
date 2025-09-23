import pandas as pd
import matplotlib.pyplot as plt
import re
from collections import Counter
#download csv
df = pd.read_csv("booking_like_hotel_reviews.csv")

#TASK 1. SENTIMENT
#add sentiment column
def sentiment (score):
    if(score>8) : return "positive"
    elif(score>=6) : return "neutral"
    elif(score<6) : return "negative"

df["sentiment"] = df["rating_overall"].apply(sentiment)

#count
sentiment_counts = df["sentiment"].value_counts(normalize=True)*100

#pie chart
sentiment_array = sentiment_counts.values
sentiment_labels = sentiment_counts.index.tolist()

#plot
plt.pie(sentiment_array, labels=(sentiment_labels), autopct='%1.1f%%')
plt.title("Sentiment Distribution")
plt.show()


#TASK 2. ROLLING AVG

#average rating by day
dailyAvg = df.groupby("review_date")["rating_overall"].mean()

#rolling avg 7 days
rollingAvg = dailyAvg.rolling(window = 7).mean()

#plot
plt.figure()
plt.plot(dailyAvg.index, dailyAvg.values, label="Daily Average", color="#FAAB01")
plt.plot(rollingAvg.index, rollingAvg.values, label="Rolling Average", color="red")
plt.xlabel("Date")
plt.ylabel("Average Rating")
plt.legend()
plt.show()


#TASK 3. NLP
texts = df["review_text"].dropna().astype(str)
stopwords = set("a an the and or of to in is are was were be been very with for from on at by good great nice bad poor room rooms staff breakfast location wifi wi-fi".split())

#tokenization
words = []
for t in texts:
    t = t.lower()
    t = re.sub(r"[^a-zA-Zà-ž0-9\s]", "", t)
    for w in t.split():
        if(len(w)<3): continue
        if w in stopwords: continue
        words.append(w)
        
#15  most popular words
word_counts = Counter(words).most_common(15)
labels, values = zip(*word_counts)

#plot
plt.figure()
plt.bar(labels, values, color="skyblue")
plt.xticks(rotation=45)#anfolo delle parole in modo che le parole non si sovrappongono
plt.title("Top 15 Words in Hotel Reviews")
plt.ylabel("Count")
plt.show()


    

