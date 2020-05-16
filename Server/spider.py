import requests
from bs4 import BeautifulSoup
import re
import json
import datetime
import os
import shutil
import importlib,sys

importlib.reload(sys)



contentlist=['div[id="vsb_content"]','div[id="vsb_content_2"]','div[id="vsb_content_3"]','div[id="vsb_content_6"]','div[id="vsb_content_7"]']

today=datetime.date.today() 
today_string=str(today)
today_month=int(today_string[5]+today_string[6])
today_day=int(today_string[8]+today_string[9])
w=0

class News:
    url=''       
    title=''     
    date=datetime.date(2000,2,2)      
    department=''    
    content=''       
    
    def __init__(self,u,t,d,con,dep):
        self.url = u  
        self.title = t
        self.date=d
        self.content=con
        self.department=dep   
    


class department_support:           
    
    date_select=''        
    title_select=''        
    url_head=''   
    url_regular='' 
    title_regular=''   
    date_regular=''    
    department=''   
    content_support=''  
    
    def __init__(self,ds,ts,uh,ur,tr,dr,department1,con_support):
        self.date_select=ds
        self.title_select=ts
        self.url_head=uh
        self.url_regular=ur
        self.title_regular=tr
        self.date_regular=dr
        self.department=department1
        self.content_support=con_support
    

def Get_html(url1):
    headers = {'User-Agent' : 'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}
    r=requests.get(url1,headers=headers)
    r.encoding="UTF-8"
    soup0=BeautifulSoup(r.text,"html.parser")
    return soup0


def Deal_Text(strings):     
    output1=''.join(strings)     
    output2=re.sub('<(.*?)>','',str(output1))    
    return str(output2)

                                
def Deal_date(d):
    if len(d)==5:               
        if (int(d[0]+d[1])>today_month) or ((int(d[3]+d[4])>today_day) and (int(d[0]+d[1])==today_month)):
            year=2019
        else:
            year=2020
        month=int(d[0]+d[1])
        day=int(d[3]+d[4])
    elif len(d)==10:
        year=int(d[0]+d[1]+d[2]+d[3])
        month=int(d[5]+d[6])
        day=int(d[8]+d[9])
    else:
        year=day=month=0    
    date_news=datetime.date(year,month,day)
    return date_news

def Get_content(web_url,support):
    soup_text=Get_html(web_url)
    content=soup_text.select(str(support))         
    text=re.findall('>(.*?)<',str(content))
    if len(text)==0:        
       for i in range(0,5):
         if contentlist[i]==str(support):
            continue
         else:
            content=soup_text.select(str(contentlist[i]))   
            text=re.findall('>(.*?)<',str(content))
            if len(text)>0:
                 break
    output=Deal_Text(text)
    return str(output)

def mkdirall(path):         
    folder = os.path.exists(path)
 
    if not folder:                   
        w=1
    else:                                       
        os.makedirs(path+'/'+'School_department')
        os.makedirs(path+'/'+'Electronics_department')
        os.makedirs(path+'/'+'Scicence_department')
        os.makedirs(path+'/'+'Math_department')
        os.makedirs(path+'/'+'Machine_department')
        os.makedirs(path+'/'+'Energy_department')
        os.makedirs(path+'/'+'Chemical_department')
        os.makedirs(path+'/'+'Electric_department')


def mkdir(path):    
 
    folder = os.path.exists(path)
 
    if not folder:                  
        os.makedirs(path)            
        mkdirall(path)              
    else:
        w=2
        
def remove_all(path):
    filenames = os.listdir(path)
    for i in filenames:
        realpath=path+'/'+i
        shutil.rmtree(realpath)
    

School_department=department_support('.main.w1200.cleafix .xstz.fl .xstz_list .xstz_list_ul li .fr.lg',
                        '.main.w1200.cleafix .xstz.fl .xstz_list .xstz_list_ul li',
                        'http://dean.xjtu.edu.cn/',
                        'href="(.*)" target=',
                        'title="(.*)">',
                        '<a class="fr lg">(.*)</a>',
                        'School_department',
                        'div[id=vsb_content]')

Electronics_department=department_support('ul li span',
                                  'ul .clearfix a',
                                  'http://eieug.xjtu.edu.cn/',
                                  'href="(.*)" title=',
                                  '<p class="tit">(.*)</p>',
                                  '<span class="h2">(.*)</span>',
                                         'Electronics_department',
                                    'div[id="vsb_content"]')


Scicence_department=department_support('ul .i_mid cite',
                                    'ul .i_mid a',
                                    'http://phych.xjtu.edu.cn/',
                                    'href="(.*)" target=',
                                    'title="(.*)"',
                                    '<cite>(.*)</cite>',
                                      'Scicence_department',
                                      'div[id="vsb_content"]')


Math_department=department_support('.tzgg.jxjw.fl.tzgg1 ul p',
                                '.tzgg.jxjw.fl.tzgg1 li a',
                                'http://math.xjtu.edu.cn/',
                                'href="(.*)" target=',
                                'title="(.*)"',
                                '<p>(.*)</p>',
                                  'Math_department',
                                  'div[id="vsb_content"]')

Machine_department=department_support('.index .con2 .con.lf ul span',
                                '.index .con2 .con.lf ul a',
                                'http://mec.xjtu.edu.cn/',
                                'href="(.*?)"',
                                'title="(.*)"',
                                '<span>(.*)</span>',
                                     'Machine_department',
                                     'div[id="vsb_content_2"]')



Electric_department=department_support('.main_con .new_xwxx.fl .new_list ul span',
                                '.main_con .new_xwxx.fl .new_list ul a',
                                'http://ee.xjtu.edu.cn/',
                                'href="(.*)" target=',
                                'title="(.*)"',
                                '<span>(.*)</span>',
                                      'Electric_department',
                                      'div[id="vsb_content_2"]')


Energy_department=department_support('div[id="info_3"] .news_list.nn3 ul li span',
                                     'div[id="info_3"] .news_list.nn3 ul li a',
                                     'http://epe.xjtu.edu.cn/',
                                     'href="(.*?)">',
                                     '/strong>(.*)</a>',
                                     '<span>(.*)</span>',
                                    'Energy_department',
                                    'div[id="vsb_content"]')



Chemical_department=department_support('.main .news .scrollFrame .bor03.cont div[id="c01"] ul li span',
                                '.main .news .scrollFrame .bor03.cont div[id="c01"] ul li a',
                                'http://clet.xjtu.edu.cn/',
                                'href="(.*)" target=',
                                'title="(.*)">',
                                'class="time">(.*)</span>',
                                      'Chemical_department',
                                      'tr td[class="contentstyle66256"]')


departlist=[School_department,Electronics_department,Scicence_department,Math_department,Machine_department,Electric_department,Energy_department,Chemical_department]



path='./xjtunews'
folder=os.path.exists(path)
if not folder:
    os.makedirs(path)
remove_all(path)


for j in range(len(departlist)):
    depart=departlist[j]
    soup=Get_html(depart.url_head)
    date=soup.select(depart.date_select)
    title=soup.select(depart.title_select)


    
    XJTU_news=[]
    swap=News('w','w',datetime.date(11,11,1),'w','w')

    for i in range(min(len(title),len(date))):
        url_tail=re.search(depart.url_regular,str(title[i])).group(1)
        if url_tail[0]=='h':
            news_url=url_tail
        else:
            news_url=depart.url_head+str(url_tail)
        news_title=re.search(depart.title_regular,str(title[i])).group(1)
        news_date=re.search(depart.date_regular,str(date[i])).group(1)  
        date_object=Deal_date(str(news_date))                    
        news_url=re.sub('"(.*)','',str(news_url))  
        text=Get_content(news_url,depart.content_support)
        if i>0 and  swap.date<date_object:
           date_object=date_object-datetime.timedelta(days=365)
        news=News(news_url,news_title,date_object,text,depart.department)
        swap=news
        XJTU_news.append(news)
    
    for i in range(len(XJTU_news)):
        file = path+'/'+str(XJTU_news[i].date)      
        filename=str(i)+'.json'
        mkdir(file) 
        news_path=file+'/'+XJTU_news[i].department+'/'+filename
        folder_news = os.path.exists(news_path) 
        if not folder_news:            
            dic={'date':str(XJTU_news[i].date),'title':XJTU_news[i].title,'url':XJTU_news[i].url,'content':XJTU_news[i].content}
            with open(news_path,'w',encoding='utf-8') as file_obj:  
                json.dump(dic,file_obj)
