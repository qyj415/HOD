//本地药品库和本地存储的脚本


var db;//本地库
var serverCount=29550;//服务器数据库记录数

var localServer;//本地存储服务器
var store;//本地存储容器

var workerPool = null;//工作者池
var childId;//工作线程

//需要本地化的文件列表
var filesToCapture = [
'js/gears_init.js',
'js/md5.js',
'js/prototype.js',
'js/transferxml.js'
];


//检查是否安装了gears,如果没有在提示安装
setupGears();

//检查是否有安装gears,如果没有就提示,如果有运行程序
function setupGears() {
  if (!window.google || !google.gears) {
    if (confirm("如果要使用药品库快速查询功能,您必须先安装Gears,现在安装吗?")) {
        window.open("http://www.anycare.cn/tools/GearsSetup.exe");
        return;
    }
  }else{
  	//初始化本地库和本地存储
  	init();
  }
}

//建立库建立表,本地化部分文件,初始化workerpool
function init() {
  var success = false;
  	//初始化库
    try {
      db = google.gears.factory.create('beta.database');

      if (db) {
        db.open('database-bpm');
        //记录版本号,drugsType保存医院编码
        db.execute('create table if not exists drugsVer'  +
                   ' (drugsType varchar(255), '
                   +' ver varchar(20), '                   
                   +' verdate varchar(20));');
       
        //总库正式表
        db.execute('create table if not exists drugs'  +
                   ' (mc varchar(255), '
                   +' bm varchar(20), '
                   +' gg varchar(255), '                   
                   +' jx varchar(255), '     
                   +' py varchar(20), '                                                                       
                   +' dw varchar(20));');
         //总库零时表          
         db.execute('create table if not exists drugsTemp'  +
                   ' (mc varchar(255), '
                   +' bm varchar(20), '
                   +' gg varchar(255), '                   
                   +' jx varchar(255), '     
                   +' py varchar(20), '                                                                       
                   +' dw varchar(20));');
          //医院库正式表        
         db.execute('create table if not exists drugsHospital'  +
                   ' (yy varchar(20), '
                   +' bm varchar(20), '
                   +' sm varchar(255));');  
           //医院库零时表         
         db.execute('create table if not exists drugsHospitalTemp'  +
                   ' (yy varchar(20), '
                   +' bm varchar(20), '
                   +' sm varchar(255));');                                               
        success = true;
      }

    } catch (ex) {
      alert('不能够建立本地数据库: ' + ex.message);
    }
    //初始化本地存储
   try {
    localServer =
     google.gears.factory.create('beta.localserver');
  
     store = localServer.createStore('store-bpm');
  
     store.capture(filesToCapture, captureCallback);
    }catch(ex){
  	  alert('不能够建立本地服务器: ' + ex.message);
    }
    
    //初始化workerpool
    try {
     workerPool = google.gears.factory.create('beta.workerpool');
     workerPool.onmessage = parentHandler;
      try {
       childId = workerPool.createWorkerFromUrl('js/worker.js');
      } catch (e) {
       setError('不能够建立后台线程: ' + e.message);
      }
    } catch (e) {
     alert('不能够建立工作者池: ' + e.message);
     return;
    }

}

//本地化返回句柄
function captureCallback(url, success, captureId) {
}

//工作者线程返回句柄
function parentHandler(messageText, sender, message) {
	//如果不是本地药品库才显示进度条.
  if (messageText.indexOf("mydrugs")<0){
   setProcessStrip();
  }else{
  	//如果是本地库，一次完成，更新版本号
  	updatelocalVer(hostpal,serverMyVer);
  }
}

//设置进度条
function setProcessStrip(){
   var localCount = getLocalCount();
  var tempDrugs = ((localCount/serverCount).toFixed(3)*100)+"";
    
    if (localCount>=serverCount){
     
     document.getElementById("stripDiv").style.width="100%";
     document.getElementById("stripNum").innerText = "药品库写入完成!"
     //更新版本号
     updatelocalVer("drugs",serverVer);
     return true;
    }else{
    	document.getElementById("stripTable").style.display="";
     if (tempDrugs.length>4) tempDrugs = tempDrugs.substring(0,4);
     document.getElementById("stripDiv").style.width=tempDrugs+"%";
     document.getElementById("stripNum").innerHTML = "药品库正在写入.已写入"+tempDrugs+"%";
     return false;
   }
} 

//启动异步写入药品库
function asyncComputation(drugsJson) {
  if (workerPool) {
    workerPool.sendMessage(drugsJson, childId);
  }
}


//取本地库的记录数
function getLocalCount(){
var localCount=0;
var rs = db.execute('select count(*) from drugsTemp');
  while (rs.isValidRow()) {
    localCount = rs.field(0);
    rs.next();
  }
  rs.close();
  return localCount;
}

//取本地库的版本号
function getlocalVer(type){
	if (window.google && google.gears) {
var localCount="0.0";
try{
var rs = db.execute('select ver from drugsVer where drugsType="'+type+'"');
  //如果有记录就去版本号,如果没有添加一条记录
  if (rs.isValidRow()) {
    localCount = rs.field(0);
   // rs.next();
  }else{
   	 db.execute('insert into drugsVer values(?,?,?)', [type, '0.0','']);
  }
  rs.close();
}catch(ex){
	alert("初始化本地库版本出错!"+ex.message);
}
  return localCount;
}else{
	return "0.0";
}
}


//更新本地库的版本号
function updatelocalVer(type,ver){
var localCount=false;
try{
	 db.execute('BEGIN');
 	 db.execute('update drugsVer set ver="'+ver+'",verdate="'+(new Date())+''+'" where drugsType="'+type+'"');
 	 
 	 if ("drugs"==type){
 	 //零时总库更新到正式库
 	 db.execute('delete from drugs');
 	 db.execute('insert into drugs(mc,bm,gg,jx,py,dw) select mc,bm,gg,jx,py,dw from drugsTemp');
 	 //删除零时总库
 	 db.execute('delete from drugsTemp');
 	}else{
 		//零时医院库更新到正式库
 	 db.execute('delete from drugsHospital');
 	 db.execute('insert into drugsHospital(yy,bm,sm) select yy,bm,sm from drugsHospitalTemp');
 	 //删除零时医院库
 	 db.execute('delete from drugsHospitalTemp');
 	}
 	 db.execute('COMMIT');
}catch(ex){
	alert("更新本地库的版本出错!"+ex.message);
}
  return true;
}

//脚本暂停等待numberMillis毫秒
 function pause(numberMillis) {
        var now = new Date();
        var exitTime = now.getTime() + numberMillis;
        while (true) {
            now = new Date();
            if (now.getTime() > exitTime)
                return;
        }
}