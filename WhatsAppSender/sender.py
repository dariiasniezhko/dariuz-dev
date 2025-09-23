#python whatsapp
import time

#Send message
def sendMessage(messaggio, numbers):
	import pywhatkit
	for numero in numbers:
	#pywhatkit.sendwhatmsg(numero, messagio, 17, 35)
		try:
			pywhatkit.sendwhatmsg_instantly(numero, messaggio, wait_time=15, tab_close=True)
			print('Messaggio al numero', numero, 'Inviato con successo!')
			time.sleep(5)
		except Exception as e:
			print('Errore con il numero', numero, ':', e)