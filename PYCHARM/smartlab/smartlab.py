from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

from flask import Flask,render_template,request,redirect,jsonify,session
from DBConnection import Db
import datetime
app = Flask(__name__)
app.secret_key="123"
path=r"C:\Users\mevin\PycharmProjects\smartlab\static\image\\"


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/about')
def about():
    return render_template('about.html')



@app.route('/contact')
def contact():
    return render_template('contact.html')







@app.route('/login',methods=['GET','POST'])
def login():
    if request.method=="POST":
        username=request.form['textfield2']
        password=request.form['textfield3']
        db=Db()
        res=db.selectOne("select * from login where username='"+username+"' and  password='"+password+"'")
        if res is not None:
            session['head']=""
            if res['usertype']=='admin':
                return redirect ('/admin_home')
            else:
                return '<script>alert("INVALID USER");window.location="/"</script>'
        else:
            return '<script>alert("INVALID USER");window.location="/"</script>'
    else:
         return render_template('contact.html')

@app.route('/admin_home')
def admin_home():
    return render_template('admin/Admin_index.html')


@app.route('/add_dept',methods=['GET','POST'])
def add_dept ():
    session['head']= 'add department'
    if request.method=="POST":
        name=request.form['textfield']
        db=Db()
        q=db.selectOne("select * from department where department_name='"+name+"'")
        if q is None:
            db.insert("insert into department VALUE ('','"+name+"')")
            return '<script>alert("added");window.location="/view_department#a"</script>'
        else:
            return '<script>alert("already added");window.location="/view_department#a"</script>'
    else:
        return  render_template('admin/admin.html')


@app.route('/view_department')
def view_department():
    session['head'] = 'view department'
    db=Db()
    res=db.select("select * from department ")
    return render_template("admin/view_department.html",data=res)


@app.route('/edit_department/<did>',methods=['GET','POST'])
def edit_department(did):
    if request.method=="POST":
        name = request.form['textfield4']
        db=Db()
        db.update ("update department set department_name='"+name+"'  where dept_id='"+did+"'")
        return '<script>alert("updated");window.location="/view_department#a"</script>'
    else:
        db=Db()
        res=db.selectOne("select * from department where dept_id='"+did+"'")
        return render_template("admin/edit-department.html",data=res)

@app.route('/delete_department/<did>')
def delete_department(did):
    db=Db()
    db.delete("delete from department where dept_id='"+did+"'")
    return '<script>alert("deleted");window.location="/view_department#a"</script>'


@app.route('/add_course',methods=['GET','POST'])
def add_course():
    session['head'] = 'add course'
    if request.method=="POST":
        department=request.form['select']
        course=request.form['textfield4']
        db=Db()
        db.insert("insert into course VALUE('','"+course+"','"+department+"')")
        return '<script>alert("added");window.location="/view_course#a"</script>'
    else:
        db=Db()
        res=db.select("select * from department")
        return render_template('admin/add_course.html',data=res)


@app.route('/view_course')
def view_course():
    session['head'] = 'view course'
    db=Db()
    res=db.select("select * from  course,department where  course.department_id=department.dept_id ")
    return render_template("admin/view_course.html",data=res)

@app.route('/edit_course/<cid>',methods=['get','post'])
def edit_course(cid):
    if request.method=="POST":
      name=request.form['textfield']
      department_name=request.form['select']
      db=Db()
      db.update("update course set course_name='"+name+"',department_id='"+department_name+"' where course_id='"+cid+"'")
      return '<script>alert("updated");window.location="/view_course#a"</script>'
    else:
         db = Db()
         res = db.selectOne("select * from course where course_id='" + cid + "'")
         res1 = db.select("select * from department")
         return render_template("admin/edit_course.html",data=res,data1=res1)

@app.route('/delete_course/<cid>')
def delete_course(cid):
    db=Db()
    db.delete("delete from course where course_id='"+cid+"'")
    return '<script>alert("deleted");window.location="/view_course#a"</script>'


@app.route('/addlab',methods=['GET','POST'])
def addlab():
    session['head'] = 'add lab'
    if request.method=="POST":
        name=request.form['textfield']
        no=request.form['textfield2']
        db=Db()
        db.insert("insert into lab VALUES ('','"+name+"','"+no+"')")
        return '<script>alert("added succesfully");window.location="/addlab"</script>'
    else:
        return render_template('admin/add_lab.html')

@app.route('/view_lab')
def view_lab():
    session['head'] = 'view lab'
    db=Db()
    res=db.select("select * from lab")


    return render_template('admin/view_lab.html',data=res)

@app.route('/editlab/<lid>',methods=['GET','POST'])
def editlab(lid):
    if request.method=="POST":
        name=request.form['textfield']
        no=request.form['textfield2']
        db=Db()
        db.update("update lab set lab_name='"+name+"',room_no='"+no+"'where lab_id='"+lid+"'")
        return '<script>alert("updated succesfully");window.location="/view_lab#a"</script>'
    else:
        db=Db()
        res=db.selectOne("select * from lab where lab_id='"+lid+"'")
        return render_template('admin/edit_lab.html',data=res)

@app.route('/delete_lab/<lid>')
def delete_lab(lid):
    db=Db()
    db.delete("delete from lab where lab_id='"+lid+"'")
    return '<script>alert("deleted");window.location="/view_lab#a"</script>'



@app.route('/addsubject/<lid>',methods=['GET','POST'])
def addsubject(lid):
    session['head'] = 'add subject'
    if request.method=="POST":
        course_name=request.form['select']
        sub_name=request.form['textfield']
        sem=request.form['select1']
        db=Db()
        db.insert("insert into lab_subject VALUES('','"+lid+"','"+course_name+"','"+sub_name+"','"+sem+"')")
        return '<script>alert("added");window.location="/view_lab#a"</script>'
    else:
        db = Db()
        # a = db.select("select * from course,lab_subject where course.course_id=lab_subject.course_id AND course.course_id=lab_subject.course_id")
        # a=db.select("select * from course,lab_subject WHERE course.course_id=lab_subject.course_id AND course.course_name=lab_subject.course_id")
        a=db.select("select * from course")
        return render_template('admin/add_lab_subject.html',data=a)

@app.route('/viewsubject/<lid>')
def viewsubject(lid):
    session['head'] = 'view subject'
    db=Db()
    res = db.select("select * from lab_subject,course where lab_subject.course_id=course.course_id and lab_subject.lab_id='"+lid+"'")
    return render_template("admin/view_lab_subject.html",data=res)

@app.route('/delete_labsubject/<tid>')
def delete_labsubject(tid):
    db=Db()
    db.delete("delete from lab_subject where labsub_id='"+tid+"'")
    return '<script>alert("deleted");window.location="/view_lab#a"</script>'












@app.route('/addstaff',methods=['get','post'])
def add_staff ():
    session['head'] = 'add staff'
    if request.method=="POST":
        name=request.form['textfield']
        gender=request.form['RadioGroup1']
        DOB=request.form['textfield2']
        email=request.form['textfield3']
        phone=request.form['textfield4']
        image=request.files['fileField']
        date=datetime.datetime.now().strftime("%y%m%d-%H%M%S")
        image.save(r"C:\Users\mevin\PycharmProjects\smartlab\static\image\\"+date+'.jpg')
        p="/static/image/"+date+'.jpg'
        # date=datetime.datetime.now().strftime("%y%m%d-%H%M%S")
        # image.save(r"C:\Users\mevin\PycharmProjects\smartlab\static\image\\+date+'.jpg'")
        # p="/static/image/"+date+'.jpg'
        department=request.form['select']
        db=Db()
        import random
        pas=str(random.randint(0000,9999))
        q=db.insert("insert into login values('','"+email+"','"+pas+"','staff')")
        db.insert("insert into staff VALUES('"+str(q)+"','"+name+"','"+gender+"','"+DOB+"','"+email+"','"+phone+"','"+str(p)+"','"+department+"')")

        import smtplib

        s = smtplib.SMTP(host='smtp.gmail.com', port=587)
        s.starttls()
        s.login("mevinmevi88@gmail.com", "dlspkxkicusxyfmg")
        msg = MIMEMultipart()  # create a message.........."
        msg['From'] = "mevinmevi88@gmail.com"
        msg['To'] = email
        msg['Subject'] = "Your Password for Smartlab"
        body = "Your Password is:- - " + str(pas)
        msg.attach(MIMEText(body, 'plain'))
        s.send_message(msg)

        return '<script>alert("added succesfully");window.location="/view_staff#a"</script>'
    else:
        db = Db()
        a = db.select("select * from department")
        return render_template('admin/add_staff.html',data=a)

@app.route('/view_staff')
def view_staff():
    session['head'] = 'view staff'
    db=Db()
    res=db.select("select * from staff,department where staff.dept_id=department.dept_id")
    return render_template('admin/view_staff.html',data=res)

@app.route('/edit_staff/<did>',methods=['get','post'])
def edit_staff(did):
    session['head'] = 'edit staff'
    if request.method=="POST":
        name=request.form['textfield']
        gender=request.form['radio']
        DOB=request.form['textfield2']
        email=request.form['textfield3']
        phone=request.form['textfield4']
        image=request.files['fileField']
        date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
        image.save(r"C:\Users\mevin\PycharmProjects\smartlab\static\image\\" + date + '.jpg')
        p = "/static/image/" + date + '.jpg'
        department=request.form['select']
        if request.files!="":
            if image.filename!="":
                db=Db()
                db.update("update staff set name='"+name+"',gender='"+gender+"',DOB='"+DOB+"',email='"+email+"',phone='"+phone+"',image='"+str(p)+"',dept_id='"+department+"'where staff_id='"+str(did)+"'")
                return '<script>alert("edited succesfully");window.location="/view_staff#a"</script>'
            else:
                db = Db()
                db.update("update staff set name='" + name + "',gender='" + gender + "',DOB='" + DOB + "',email='" + email + "',phone='" + phone + "',dept_id='" + department + "'where staff_id='" + str(did) + "'")
                return '<script>alert("edited succesfully");window.location="/view_staff#a"</script>'
        else:
            db = Db()
            db.update("update staff set name='" + name + "',gender='" + gender + "',DOB='" + DOB + "',email='" + email + "',phone='" + phone + "',dept_id='" + department + "'where staff_id='" + str(did) + "'")
            return '<script>alert("edited succesfully");window.location="/view_staff#a"</script>'

    else:
        db=Db()
        res=db.selectOne("select * from staff where staff_id='" + did + "'")
        res1=db.select("select * from department")
        return render_template('admin/edit_staff.html', data=res,data1=res1)

@app.route('/delete_staff/<did>')
def delete_staff(did):
    db=Db()
    db.delete("delete from staff where staff_id='"+did+"'")
    return '<script>alert("deleted");window.location="/view_staff#a"</script>'







@app.route('/addstudent',methods=['get','post'])
def add_student():
    session['head']='add student'
    if request.method == "POST":
        name = request.form['textfield']
        reg_no = request.form['textfield2']
        course = request.form['s']
        sem = request.form['textfield4']
        email = request.form['textfield5']
        phone = request.form['textfield6']
        image = request.files['fileField']
        rfid = request.form['textfield7']
        date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
        image.save(r"C:\Users\mevin\PycharmProjects\smartlab\static\image\\" + date + '.jpg')
        p = "/static/image/" + date + '.jpg'
        db = Db()
        db.insert("insert into student VALUES('','" + name + "','" + reg_no + "','" + course + "','" + sem + "','" + email + "','" + phone + "','" + p + "','"+rfid+"')")
        return '<script>alert("added succesfully");window.location="/view_student#a"</script>'
    else:
        db=Db()
        a=db.select("select * from course")
        return render_template('admin/add_student.html',data=a)


@app.route('/view_student')
def view_student():
    session['head']='view student'
    db = Db()
    res = db.select("select * from student,course where student.course_id=course.course_id ")
    return render_template('admin/view_student.html',data=res)


@app.route('/edit_student/<sid>',methods=['get','post'])
def edit_student(sid):
    session['head'] = 'edit student'
    if request.method=="POST":
        name = request.form['textfield']
        reg_no = request.form['textfield2']
        course = request.form['select']
        sem = request.form['select2']
        email = request.form['textfield3']
        phone = request.form['textfield4']
        rfid = request.form['textfield7']
        image = request.files['fileField']
        if request.files is not None:
            if image.filename != "":
                date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
                image.save(r"C:\Users\mevin\PycharmProjects\smartlab\static\image\\" + date + '.jpg')
                p = "/static/image/" + date + '.jpg'
                db=Db()
                db.update("update student set name='"+name+"',reg_no='"+reg_no+"',course_id='"+course+"',sem='"+sem+"',email='"+email+"',phone='"+phone+"',photo='"+str(p)+"', rfid='"+rfid+"' where student_id='"+str(sid)+"'")
                return '<script>alert("edited succesfully");window.location="/view_student#a"</script>'
            else:
                db = Db()
                db.update("update student set name='" + name + "',reg_no='" + reg_no + "',course_id='" + course + "',sem='" + sem + "',email='" + email + "',phone='" + phone + "', rfid='"+rfid+"' where student_id='" + str(sid) + "'")
                return '<script>alert("edited succesfully");window.location="/view_student#a"</script>'
        else:
            db = Db()
            db.update(
                "update student set name='" + name + "',reg_no='" + reg_no + "',course_id='" + course + "',sem='" + sem + "',email='" + email + "',phone='" + phone + "', rfid='"+rfid+"' where student_id='" + str(sid) + "'")
            return '<script>alert("edited succesfully");window.location="/view_student#a"</script>'
    else:
        db=Db()
        res=db.selectOne("select * from student where student_id='"+sid+"'")
        res1=db.select("select * from course")

        return render_template('admin/edit_student.html',data=res,data1=res1)



@app.route('/delete_student/<sid>')
def delete_student(sid):
    db=Db()
    db.delete("delete from student where student_id='"+sid+"'")
    return '<script>alert("deleted");window.location="/view_student#a"</script>'

@app.route('/add_lab_subject/<d>',methods=['GET','POST'])
def add_lab_subject(d):
    if request.method=="POST":
        course_name=request.form['select']
        subject_name=request.form['select2']
        semester=request.form['textfield3']
        db=Db()
        db.insert("insert into lab_subject VALUES('','"+d+"','"+course_name+"','"+subject_name+"','"+semester+"')")
        return '<script>alert("added");window.location="/add_lab_subject#a"</script>'
    else:
        db = Db()
        a = db.select("select * from course")
        return render_template('admin/add_lab_subject.html',data=a)


@app.route('/add_lab_allocation',methods=['GET','POST'])
def add_lab_allocation ():
    session['head'] = 'add allocation'
    if request.method=="POST":
        lab_name=request.form['select']
        staff_name=request.form['select2']
        db=Db()
        q=db.selectOne("select * from allocate where lab_id='"+lab_name+"' and staff_id='"+staff_name+"'")
        if q is None:
            db.insert("insert into allocate VALUES('','"+lab_name+"','"+staff_name+"')")
            return '<script>alert("added");window.location="/view_lab_allocation#a"</script>'
        else:
            return '<script>alert("already allocated");window.location="/view_lab_allocation#a"</script>'
    else:
        db = Db()
        a = db.select("select * from lab")
        b=db.select("select * from staff")

        return render_template('admin/add_lab_allocation.html',data=a,data1=b)



@app.route('/view_lab_allocation')
def view_lab_allocation():
    session['head'] = 'view allocation'
    db = Db()
    res = db.select("select * from allocate,lab,staff where allocate.lab_id=lab.lab_id and allocate.staff_id=staff.staff_id")
    return render_template('admin/view_lab_allocation.html', data=res)


@app.route('/delete_lab_allocation/<aid>')
def delete_lab_allocation(aid):
    db=Db()
    db.delete("delete from allocate where allocate_id='"+aid+"'")
    return '<script>alert("deleted");window.location="/view_lab_allocation#a"</script>'

@app.route('/add_exam_scheduling',methods=['get','post'])
def add_exam_scheduling():
    session['head'] = 'add exam'
    if request.method=="POST":
        lab_subject=request.form['select']
        date=request.form['textfield']
        start_time=request.form['textfield2']
        end_time=request.form['textfield3']
        description=request.form['textarea']
        db=Db()
        db.insert("insert into exam values('','"+lab_subject+"','"+date+"','"+start_time+"','"+end_time+"','"+description+"')")
        return '<script>alert("added successfully");window.location="/view_exam_scheduling#a"</script>'


    else:
        db=Db()
        res=db.select("select * from lab_subject ")
        return render_template("admin/add_exam_scheduling.html",data=res,d=datetime.datetime.now().strftime("%Y-%m-%d"))


@app.route('/view_exam_scheduling')
def view_exam_scheduling():
    session['head'] = 'view exam'
    db = Db()
    res = db.select("select * from exam,lab_subject where exam.labsub_id=lab_subject.labsub_id")
    return render_template('admin/view_exam_scheduling.html',data=res)


@app.route('/edit_exam_scheduling/<lid>',methods=['get','post'])
def edit_exam_scheduling(lid):
    session['head'] = 'edit exam'
    if request.method=="POST":
        lab_subject = request.form['select']
        date = request.form['textfield']
        start_time = request.form['textfield2']
        end_time = request.form['textfield3']
        description = request.form['textarea']
        db = Db()
        db.update("update exam set date='"+date+"',start_time='"+start_time+"',end_time='"+end_time+"',description='"+description+"' where exam_id ='" + lid + "'")
        return '<script>alert("updated succesfully");window.location="/view_exam_scheduling#a"</script>'
    else:
        db = Db()
        res = db.selectOne("select * from exam where exam_id='" + lid + "'")
        res1=db.select("select * from lab_subject")
        return render_template('admin/edit_exam_scheduling.html', data=res,data1=res1)



@app.route('/delete_exam_scheduling/<lid>')
def delete_exam_scheduling(lid):
    db=Db()
    db.delete("delete from exam where exam_id='"+lid+"'")
    return '<script>alert("deleted");window.location="/view_exam_scheduling#a"</script>'







@app.route('/edit_lab')
def edit_lab():
    return render_template('admin/edit_lab.html')














#
# @app.route('/view_lab_subject')
# def view_lab_subject():
#     return render_template('admin/view_lab_subject.html')
#
#     return render_template('admin/view_staff.html')


# ================================================================================================================================================
#                                                 STAFF MODULE
# ================================================================================================================================================


@app.route('/and_login',methods=['post'])
def and_login():
        username=request.form['u']
        password=request.form['p']
        db=Db()
        res = db.selectOne("select * from login where username='" + username + "' and  password='" + password + "'")
        if res is not None:
            return jsonify(status="ok",type=res['usertype'],lid=res['login_id'])
        else:
            return jsonify(status="no")


@app.route('/view_allocated_lab',methods=['post'])
def view_allocated_lab():
    lid=request.form['id']
    db=Db()
    res=db.select("select * from  allocate,lab where allocate.lab_id=lab.lab_id and staff_id='"+lid+"' ")
    print("select * from  allocate,lab where allocate.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)



@app.route('/add_system',methods=['post'])
def add_system():
        name = request.form['name']
        mac_id = request.form['macid']
        lab_id=request.form['lb']
        print(name,mac_id,lab_id)
        db=Db()
        db.insert("insert into system values('', '"+lab_id+"','"+name+"','"+mac_id+"')")
        return jsonify(status="ok")


@app.route('/view_system',methods=['post'])
def view_system():
    lid = request.form['id']
    db = Db()
    print(lid)
    res=db.select("select * from system,allocate where system.lab_id=allocate.lab_id and allocate.staff_id='"+lid+"'")
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)



@app.route('/and_delete',methods=['post'])
def and_delete():
    sys_id=request.form['sys_id']
    db=Db()
    db.delete("delete from system where sys_id='"+sys_id+"'")
    return jsonify(status="ok")


@app.route('/set_permission',methods=['post'])
def set_permission():
    lid=request.form['lb']
    app_name=request.form['name']
    # print(lid,app_name)
    db=Db()
    db.insert("insert into blockedapps values('','"+lid+"','"+app_name+"')")
    return jsonify(status="ok")

@app.route('/blocked_apps',methods=['post'])
def blocked_apps():
    lid = request.form['id']
    db = Db()

    res=db.select("select * from blockedapps WHERE  staff_id='"+lid+"'")
    print(res)
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)

@app.route('/an_delete',methods=['post'])
def an_delete():
    block_id=request.form['block_id']
    db=Db()
    db.delete("delete from blockedapps where block_id='"+block_id+"'")
    return jsonify(status="ok")



@app.route('/create_process',methods=['post'])
def create_process():
    lid=request.form['lb']
    process_name=request.form['name']
    # print(lid,app_name)
    db=Db()
    db.insert("insert into startapps values('','"+lid+"','"+process_name+"')")
    return jsonify(status="ok")

@app.route('/shutdown',methods=['post'])
def shutdown():
    sid=request.form['sys_id']
    # process_name=request.form['name']
    # print(lid,app_name)
    db=Db()
    db.insert("insert into command values('','"+sid+"','shutdown')")
    return jsonify(status="ok")


@app.route('/restart',methods=['post'])
def restart():
    sid=request.form['sys_id']
    # process_name=request.form['name']
    # print(lid,app_name)
    db=Db()
    db.insert("insert into command values('','"+sid+"','restart')")
    return jsonify(status="ok")





@app.route('/view_running_process',methods=['post'])
def view_running_process():
    lid = request.form['sid']
    db = Db()

    res=db.select("select * from applogs where sys_id='"+lid+"' order by app_id desc")
    print(res)
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)


@app.route('/view_key_logs',methods=['post'])
def view_key_logs():
    lid = request.form['sid']
    db = Db()

    res=db.select("select * from keylogs where `sys-id`='"+lid+"' ")
    print(res)
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)


@app.route('/screenshot',methods=['post'])
def screenshot():
    sid = request.form['sid']
    db = Db()
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    cid=db.insert("insert into capture(date, sys_id, time, path, type) values(curdate(), '"+sid+"', curtime(), 'pending', 'screenshot')")
    import time
    time.sleep(4)
    res=db.select("select * from capture where sys_id='"+str(sid)+"' order by capture_id desc")
    return jsonify(status="ok", data=res)



@app.route('/lab_exam_system_allocation',methods=['post'])
def lab_exam_system_allocation():
    lid = request.form['sid']
    db = Db()

    res=db.select("select * from exam where `sys-id`='"+lid+"' ")
    print(res)
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)


@app.route('/and_view_student_attendance',methods=['post'])
def and_view_student_attendance():
    lid = request.form['id']
    db = Db()
    res=db.select("select  attendance.*,student.name as sname,course.*,staff.name as stname  from staff,attendance,student,course where attendance.student_id=student.student_id and course.course_id=student.course_id and course.department_id=staff.dept_id and staff.staff_id='"+lid+"' ")
    if len(res)>0:
        return jsonify(status="ok",data=res)
    else:
        return jsonify(status="no")

@app.route('/exam_scheduling', methods=['post'])
def exam_scheduling():
        if request.method == "POST":
            lab_id = request.form['labid']
            date = request.form['date']
            start_time = request.form['starttime']
            end_time = request.form['endtime']
            description = request.form['description']
            db = Db()
            db.insert("insert into exam values('','" + lab_id + "','" + date + "','" + start_time + "','" + end_time + "','" + description + "')")
        return jsonify(status="ok")


@app.route('/view_exam_schedule',methods=['post'])
def view_exam_schedule():
    db = Db()
    res = db.select("select * from lab")
    return jsonify(status="ok",data=res)


@app.route('/lab_scheduling',methods=['post'])
def lab_scheduling():
    lid = request.form['id']
    db = Db()

    res=db.select("select * from exam,lab_subject,allocate,course,lab where lab_subject.course_id = course.course_id and  exam.labsub_id=lab_subject.labsub_id and allocate.lab_id=lab_subject.lab_id and allocate.lab_id=lab.lab_id and allocate.staff_id='"+lid+"'")
    # print("select * from exam,lab_subject,allocate,course,lab,staff where exam.labsub_id=lab_subject.labsub_id and allocate.lab_id=lab_subject.lab_id and allocate.lab_id=lab.lab_id and allocate.staff_id='"+lid+"'")
    print(res)
    # res=db.select("select * from system,lab where system.lab_id=lab.lab_id and staff_id='"+lid+"'")
    return jsonify(status="ok",data=res)

#########           ANDROID SYSTEM ALLOCATION TO STUDENT FOR EXAM

@app.route("/and_course_load", methods=['post'])
def and_course_load():          #   get course of subjects in allocated labs
    lid=request.form['lid']
    db=Db()
    res=db.select("select *  from course, lab_subject, allocate where course.course_id =lab_subject.course_id and lab_subject.lab_id=allocate.lab_id and allocate.staff_id='"+lid+"' group by course.course_id")
    print(res)
    return jsonify(status="ok", data=res)


@app.route("/and_student_load", methods=['post'])
def and_student_load():
    cid=request.form['cid']
    sem=request.form['sem']
    db=Db()
    res=db.select("select * from student where course_id='"+cid+"' and sem='"+sem+"'")
    return jsonify(status="ok", data=res)

@app.route("/and_exam_load", methods=['post'])
def and_exam_load():            #       get details about upcoming exams for subejcts in selected course and semester
    cid=request.form['cid']
    sem=request.form['sem']
    db=Db()
    res=db.select("select lab_subject.sub_name, exam.date, exam.exam_id from  exam,lab_subject where lab_subject.labsub_id=exam.labsub_id and exam.date>curdate() and lab_subject.course_id='"+cid+"' and lab_subject.sem='"+sem+"'")
    print("select lab_subject.sub_name, exam.date, exam.exam_id from  exam,lab_subject where lab_subject.labsub_id=exam.labsub_id and exam.date>curdate() and lab_subject.course_id='"+cid+"' and lab_subject.sem='"+sem+"'")
    return jsonify(status="ok", data=res)

@app.route("/and_system_load", methods=['post'])
def and_system_load():            #       get details about upcoming exams for subejcts in selected course and semester
    lid=request.form['lid']
    db=Db()
    res=db.select("select system.sys_id, system.sys_name, lab.lab_name from system, lab, allocate  where system.lab_id=lab.lab_id and lab.lab_id=allocate.lab_id and allocate.staff_id='"+lid+"'")
    return jsonify(status="ok", data=res)

@app.route("/and_insert_system_allocation", methods=['post'])
def and_insert_system_allocation():
    examid=request.form['examid']
    studid=request.form['studid']
    sysid=request.form['sysid']
    print(examid, studid, sysid)
    db=Db()
    res=db.selectOne("select * from exam_arrangement where student_id='"+studid+"' and exam_id='"+examid+"'")
    db=Db()
    res2=db.selectOne("select * from exam_arrangement where sys_id='"+sysid+"' and exam_id='"+examid+"'")
    if res is None and res2 is None:
        db=Db()
        db.insert("insert into exam_arrangement values('','" + examid + "','" + studid + "','" + sysid + "')")
        return jsonify(status="ok")
    elif res is not None and res2 is not None:
        return jsonify(status="both")
    elif res is not None:
        return jsonify(status="stud")
    elif res2 is not None:
        return jsonify(status="sys")

@app.route("/and_view_arrangement", methods=['post'])
def and_view_arrangement():
    exam_id=request.form['exam_id']
    db=Db()
    res=db.select("select student.name, system.sys_name, exam_arrangement.arr_id from student, system, exam_arrangement where exam_arrangement.student_id=student.student_id and exam_arrangement.sys_id=system.sys_id and exam_arrangement.exam_id='"+exam_id+"'")
    return jsonify(status="ok", data=res)

@app.route("/and_delete_system_allocation", methods=['post'])
def and_delete_system_allocation():
    alloc_id=request.form['alloc_id']
    db=Db()
    db.delete("delete from exam_arrangement where arr_id='"+alloc_id+"'")
    return jsonify(status="ok")


if __name__ == '__main__':
    app.run(port=5000,host="0.0.0.0")
