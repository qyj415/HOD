function checkCertificate(){
	var cardType = $('#cardType').val();
	var number = $('#clientIdentity').val();
	if(cardType=="0"){
		if(number==""){
			$('#clientIdentityPro').html("必填，请正确填写您的身份证号码");
			return false;
		}
		//check format
		var reg = /^[a-zA-Z0-9]+$/;
		var result = reg.test(number);
		if(result==false){
			$('#clientIdentityPro').html("<font color='red'>身份证只能包含数字和字母！</font>");
			return false;
		}
		
		//check length
		if(number.length!=18){
			$('#clientIdentityPro').html("<font color='red'>身份证号码长度为18位！</font>");
			return false;
		}
		
		
		var lastChar = number.substr(number.length-1,1);
		
		var i = (number.substr(0, 1) * 7 + number.substr(1, 1) * 9
		       + number.substr(2, 1) * 10 + number.substr(3, 1) * 5
		       + number.substr(4, 1) * 8 + number.substr(5, 1) * 4
		       + number.substr(6, 1) * 2 + number.substr(7, 1) * 1
		       + number.substr(8, 1) * 6 + number.substr(9, 1) * 3
		       + number.substr(10, 1) * 7 + number.substr(11, 1) * 9
		       + number.substr(12, 1) * 10 + number.substr(13, 1) * 5
		       + number.substr(14, 1) * 8 + number.substr(15, 1) * 4
		       + number.substr(16, 1)* 2) % 11;
		if(i>2){
			i = 12 - i;
		}else if(i==2){
			i = 'x';
		}else if(i<2){
			i = 1 - i;
		}
		
		if(lastChar=='X'){
			lastChar = 'x';
		}
		if(lastChar!=i){
			$('#clientIdentityPro').html("<font color='red'>身份证号码格式错误！</font>");
			return false;
		}
		
		var date = number.substring(6,14);
		var year = date.substr(0,4);
		var month = date.substr(4,2);
		var day = date.substr(6,2);
		if(Number(year)<1800||Number(year)>2300){
			$('#clientIdentityPro').html("<font color='red'>身份证号码格式错误！</font>");
			return false;
		}
		
		if(Number(month)<1||Number(month)>12){
			$('#clientIdentityPro').html("<font color='red'>身份证号码格式错误！</font>");
			return false;
		}
		
		if(Number(day)<1||Number(day)>31){
			$('#clientIdentityPro').html("<font color='red'>身份证号码格式错误！</font>");
			return false;
		}
	}else if(cardType=="1"){
		if(number==""){
			$('#clientIdentityPro').html("必填，请正确填写您的港澳通行证号码");
			return false;
		}
		//check format
		var reg = /^[HMhm]{1}[0-9]{10}$/;
		var result = reg.test(number);
		if(result==false){
			$('#clientIdentityPro').html("<font color='red'>请正确填写您的港澳通行证！</font>");
			return false;
		}
	}else if(cardType=="2"){
		if(number==""){
			$('#clientIdentityPro').html("必填，请正确填写您的台湾通行证号码");
			return false;
		}
		//check format
		var reg1 = /^[0-9]{8}$/;
		var reg2 = /^[0-9]{10}$/;
		var result1 = reg1.test(number);
		var result2 = reg2.test(number);
		if(!(result1||result2)){
			$('#clientIdentityPro').html("<font color='red'>请正确填写您的台湾通行证！</font>");
			return false;
		}
	}else if(cardType=="3"){
		if(number==""){
			$('#clientIdentityPro').html("必填，请正确填写您的护照号码");
			return false;
		}
		//check format
		var reg1 = /^[a-zA-Z]*$/;
		var reg2 = /^[a-zA-Z0-9]{5,17}$/;
		var result1 = reg1.test(number);
		var result2 = reg2.test(number);
		if(result1){
			$('#clientIdentityPro').html("<font color='red'>请正确填写您的护照！</font>");
			return false;
		}
		if(!result2){
			$('#clientIdentityPro').html("<font color='red'>请正确填写您的护照！</font>");
			return false;
		}
	}
	
	$('#clientIdentityPro').html('<img style="width:15px;height:15px;" src="./common/js/ok.gif" />');
	return true;
}

function checkName(){
	var name = $("#clientName").val();
	if(name==""){
		$('#namePro').html("必填，2-32位字符");
		return false;
	}
	
	//check format
	var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	var result = reg.test(name);
	if(result==false){
		$('#namePro').html("<font color='red'>只能输入字母、数字和中文字符！</font>");
		return false;
	}
	
	//check length
	if(name.length<2||name.length>32){
		$('#namePro').html("<font color='red'>姓名长度为2-32个字符！</font>");
		return false;
	}
	
	$('#namePro').html('<img style="width:15px;height:15px;" src="./common/js/ok.gif" />');
	return true;
}

function checkAddress(){
	var address = $("#clientAddress").val();
	if(address==""){
		$('#addressPro').html("必填，2-128位字符");
		return false;
	}
	
	//check format
	var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	var result = reg.test(address);
	if(result==false){
		$('#addressPro').html("<font color='red'>只能输入字母、数字和中文字符！</font>");
		return false;
	}
	
	//check length
	if(address.length<2||address.length>128){
		$('#addressPro').html("<font color='red'>地址长度为2-128个字符！</font>");
		return false;
	}
	
	$('#addressPro').html('<img style="width:15px;height:15px;" src="./common/js/ok.gif" />');
	return true;
}

function checkTel(){
	var tel = $("#clientTel").val();
	if(tel==""){
		$('#telPro').html("可填，0-15位字符,请不要使用字符'-'");
		return true;
	}
	
	//check format
	var reg = /^[0-9]+$/;
	var result = reg.test(tel);
	if(result==false){
		$('#telPro').html("<font color='red'>只能输入数字！</font>");
		return false;
	}
	
	//check length
	if(tel.length>15){
		$('#telPro').html("<font color='red'>电话号码不能超过15个字符！</font>");
		return false;
	}
	
	$('#telPro').html('<img style="width:15px;height:15px;" src="./common/js/ok.gif" />');
	return true;
}

function checkRemark(){
	var remark = $("#clientRemark").val();
	if(remark==""){
		$('#remarkPro').html("可填，0-128位字符");
		return true;
	}
	
	//check length
	if(remark.length>128){
		$('#remarkPro').html("<font color='red'>不能超过128个字符！</font>");
		return false;
	}
	
	$('#remarkPro').html('<img style="width:15px;height:15px;" src="./common/js/ok.gif" />');
	return true;
}


		function toggle(targetid,curDivId)
		{
			 var target=document.getElementById(targetid);
			 var curDiv=document.getElementById(curDivId);
			 if (target.style.display=="block")
			 {
				  target.style.display="block";
				  curDiv.style.display="none";
			 } 
			 else 
			 {
				  target.style.display="none";
				  curDiv.style.display="block";
			 }
		}
		
function checkClientaddForm(){
	var res1 = checkCertificate();
	var res2 = checkName();
	var res3 = checkAddress();
	var res4 = checkTel();
	var res5 = checkRemark();
	var name = $("#clientName").val();
	var number = $('#clientIdentity').val();
	var address = $("#clientAddress").val();
	var priceType = $("#priceType").val();
	var roomId = $("#roomId").val();
	var clientRemark=$("#clientRemark").val();
	var tel = $("#clientTel").val();
	var cardType = $("#cardType").val();
	var meters=$("#meters").html();
	var clientSex=$("input[name='hod2000Client.clientSex']:checked").val();
	 		$("#clientName2").html(name);
			if(cardType=='0'){
				$("#cardType2").html("二代身份证");
			}else if(cardType=='1'){
				$("#cardType2").html("港澳通行证");
			}else if(cardType=='2'){
				$("#cardType2").html("台湾通行证");
			}else if(cardType=='3'){
				$("#cardType2").html("护照");
			}
			$("#clientIdentity2").html(number);
			$("#clientAddress2").html(address);
			if(priceType=='1'){
				$("#priceType2").html("定额价格方案");
			}else if(priceType=='2'){
				$("#priceType2").html("阶梯价格方案一");
			}else if(priceType=='3'){
				$("#priceType2").html("阶梯价格方案二");
			}
			
			$("#roomId2").html(roomId);
			$("#clientRemark2").html(clientRemark);
			$("#clientTel2").html(tel);
			$("#clientSex2").html(clientSex);
			$("#meters2").html(meters);
	
	if(!(res1&&res2&&res3&&res4&&res5))
	{
		
		if(name==""){
			$('#namePro').html("<font color='red'>请输入用户姓名！</font>");
		}	
		if(number==""){
			$('#clientIdentityPro').html("<font color='red'>请输入证件号码！</font>");
		}
		if(address==""){
			$('#addressPro').html("<font color='red'>请输入联系地址！</font>");
		}
		if(roomId==""){
			$('#roomPro').html("<font color='red'>请选择房间信息！</font>");
		}
		return false;
	}
	
	if(roomId==""){
		$('#roomPro').html("<font color='red'>请选择房间信息！</font>");
		return false;
	}
	
	if(confirm("是否打印开户信息?"))
	{
		toggle('div3','div4');
		window.print();
	}
}