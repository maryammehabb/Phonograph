import speech_recognition as sr

r = sr.Recognizer()
with sr.Microphone() as source:
    print("Say Something")
    audio = r.listen(source)
    print("Time Over , Thanks")
try:
    print("Text: " + r.recognize_google(audio))
except:
    pass
