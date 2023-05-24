import pyttsx3
from serial import Serial, time

from DBConnection import Db

userid=''

connected = 0
serialport = 0
finalid=0




serialport = Serial('COM3', baudrate=9600)
reading = []
while True:
    while (serialport.inWaiting() > 0):
        if (serialport.inWaiting() > 0):
            reading.append(ord(serialport.read(1)))
        time.sleep(0.001)
    if reading != []:
        print(reading)
        hexstring = ''
        if str(reading[0])!="0":
            for i in reading:
                hexstring += chr(i) + ''
            print(hexstring)
            if hexstring=="cc ":
                pass
            else:
                finalid=hexstring.replace(' ','')
                rid=hexstring
                db = Db()
                res = db.selectOne(
                    "select * from student where rfid like '" + str(rid) + "%'")  # get student details from rfid number
                print(res)
                if res is None:
                    print("no stud")
                    engine = pyttsx3.init()
                    engine.say("Student not found")
                    engine.runAndWait()
                else:
                    stud_id = res['student_id']
                    db = Db()
                    res2 = db.selectOne(
                        "select * from attendance where student_id='" + str(stud_id) + "' and date=curdate()")
                    if res2 is not None:
                        print("already marked")
                        engine = pyttsx3.init()
                        engine.say("Attendance already marked")
                        engine.runAndWait()
                    else:
                        print("marked success")
                        db = Db()
                        db.insert("insert into attendance(date, student_id, status) values(curdate(), '" + str(
                            stud_id) + "', 'present')")
                        engine = pyttsx3.init()
                        engine.say("Attendance marked for '" + str(res['name']) + "' successfully")
                        engine.runAndWait()
                reading=[]



