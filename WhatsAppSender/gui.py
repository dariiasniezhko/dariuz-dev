#imports
import tkinter as tk #gui library
from tkinter import messagebox
from sender import sendMessage

def startGUI():
	def send():
		global msg_box, numbers_box
		msg = msg_box.get("1.0", tk.END).strip() # msg_box.get dal primo all'ultimo simbolo, strip - togliere gli spazi
		raw_numbers = numbers_box.get("1.0", tk.END).strip() # type: ignore # get all fields with phone numbers from the first to the last symbol
		phoneNumbers = [line.strip() for line in raw_numbers.splitlines() if line.strip()]
		#if not msg
		if not msg:
			messagebox.showwarning('Attention! Message empty!')
			return
		#if not phoneNumbers
		if not phoneNumbers:
			messagebox.showwarning('Attention! No number is inserted!')
			return
		
		sendMessage(msg, phoneNumbers) #dev'essere uguale al metodo che importi da sender a gui!
		messagebox.showinfo('Messaggi inviati con successo!')
	
	root = tk.Tk()
	root.title('WhatsApp Sender')
	root.geometry('600x500')
	root.resizable(False, False)
	root.update_idletasks()
	root.minsize(600, 500)

	global msg_box, numbers_box

	#Message field
	tk.Label(root, text='Insert your message').pack(anchor='w', pady=(10,0))
	msg_box = tk.Text(root, height=10, width=60)
	msg_box.pack(pady=5)

	#number field
	tk.Label(root, text='Phone numbers with international code (one for line) ').pack(anchor='w', pady=(10,0))
	numbers_box = tk.Text(root, height=5, width=60)
	numbers_box.pack(pady=5)

	#Send button
	tk.Button(root, text='Send', command=send).pack(pady=10) #you call a function send, defined at the beginning
	root.pack_propagate(False)
	root.mainloop()