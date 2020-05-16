from flask import Flask, jsonify
from urllib import parse  
import json
import time
import os
import pymysql
app = Flask(__name__)
@app.route('/news_get')
def newsGet():
    return newsData_v2(['School_department',])

def newsData():
    date = time.strftime("%Y-%m-%d", time.localtime())
    os.chdir(os.path.join(r'/home/xjtuhelper/news/', date))
    file_list = []
    json_data = {}
    data = []
    for root, directories, files in os.walk("."):
        for f_name in files:
            if f_name.split('.')[-1] != "json":
                raise Warning("This directory contains file without .json type")
            else:
                file_list.append(f_name)
    json_data['news_num'] = len(file_list)
    for filename in file_list:
        with open(filename) as f:
            tmp = json.load(f)
            data.append(tmp)
    json_data['data'] = data
    return json_data

def newsData_v2(campus:list):
    workspace = r'/root/xjtunews'
    os.chdir(workspace)
    json_data = {}
    data = []
    news_num = 0
    date_list = [d for d in os.listdir()]
    date_list.sort(reverse=True)
    for d in date_list:
        path = os.path.join(workspace,d)
        os.chdir(path)
        for c in campus:
            path = os.chdir(os.path.join(path,c))
            for filename in os.listdir():
                news_num += 1
                with open(filename) as f:
                    tmp = json.load(f)
                    data.append(tmp)

    json_data['news_num'] = news_num
    json_data['data'] = data
    return json_data
        
@app.route('/login/<info>')
def userLogin(info):
    info = json.loads(parse.unquote(info))
    id = info['id']
    passwd = info['passwd']
    connect = pymysql.Connect(
        host = "172.18.0.3",
        user = "root",
        passwd = "123456",
        db = "xjtuhelper"
    )
    cursor = connect.cursor()
    sql = "SELECT username,gender,college,passwd FROM user_info WHERE id = '{}';".format(id)
    cursor.execute(sql)
    result = cursor.fetchone()
    if result:
        if passwd == result[-1]:
            ret = {"status":0, "username":result[0], "gender":result[1], "college":result[2]}
        else:
            ret = {"status":1}
    else:
        ret = {"status":2}
    return ret

@app.route('/logup/<info>')
def userLogup(info):
    ret = 1
    info = json.loads(info)
    id = info['id']
    passwd = info['passwd']
    college = info['college']
    gender = info['gender']
    username = info['username']
    connect = pymysql.Connect(
        host = "172.18.0.3",
        user = "root",
        passwd = "123456",
        db = "xjtuhelper"
    )
    cursor = connect.cursor()
    sql = "SELECT id FROM user_info WHERE id = '{}';".format(id)
    cursor.execute(sql)
    registed = cursor.fetchone()
    if registed == None:
        sql = 'INSERT INTO user_info (id,username,gender,college,passwd) VALUES ("{}","{}","{}","{}","{}");'.format(id,username,gender,college,passwd)
        cursor.execute(sql)
        connect.commit()
        ret = 0
    return str(ret)

@app.route('/changeinfo/<info>')
def user_info_modify(info):
    info = json.loads(info)
    id = info['id']
    passwd = info['passwd']
    college = info['college']
    gender = info['gender']
    username = info['username']
    connect = pymysql.Connect(
        host = "172.18.0.3",
        user = "root",
        passwd = "123456",
        db = "xjtuhelper"
    )
    cursor = connect.cursor()
    sql = 'UPDATE user_info SET passwd="{}",college="{}",gender="{}",username="{}" WHERE id = "{}";'.format(passwd, college, gender, username, id)
    cursor.execute(sql)
    connect.commit()
    ret = 0
    return str(ret)

@app.route('/comment/<info>')
def comment_commit(info):
    info = json.loads(info)
    id = info['id']
    passwd = info['passwd']
    comment = info['comment']
    connect = pymysql.Connect(
    host = "172.18.0.3",
    user = "root",
    passwd = "123456",
    db = "xjtuhelper"
    )
    cursor = connect.cursor()
    sql = "SELECT username,passwd FROM user_info WHERE id = '{}';".format(id)
    cursor.execute(sql)
    result = cursor.fetchone()
    if result:
        if passwd == result[-1]:
            label = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
            print(len(label))
            sql = 'INSERT INTO user_comments (id, comment, time) VALUES ("{}", "{}", "{}")'.format(id, comment, label)
            cursor.execute(sql)
            connect.commit()
            ret = 0
        else:
            ret = 1
    else:
        ret = 2
    
    return str(ret)

@app.route('/comments_get')
def get_comment():
    connect = pymysql.Connect(
        host = "172.18.0.3",
        user = "root",
        passwd = "123456",
        db = "xjtuhelper"
    )
    cursor = connect.cursor()
    sql = "SELECT username,comment,time,c.id FROM user_comments c INNER JOIN user_info i WHERE c.id = i.id ORDER BY time DESC LIMIT 50;"
    cursor.execute(sql)
    result = cursor.fetchall()
    json_data = {}
    comments = []
    for row in result:
        comments.append({'username':row[0], 'comment':row[1], 'time':row[2], 'id':row[3]})
    
    json_data['comments'] = comments
    
    return json_data

    


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=False)
    #with open("/home/xjtuhelper/news/log.json", 'w') as f:
        #json.dump(newsData_v2(['School_department',]), f)
