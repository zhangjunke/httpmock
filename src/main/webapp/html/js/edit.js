var urlprefix="";
function modifySubmit(id) {
    var modifySubmiturl =urlprefix+ "/mocker/ModifySubmit";
    var detailid=id;
    var caseName=document.getElementById('mockCaseName2').value;
    var code=document.getElementById('mockCodeName2').value;
    var time=document.getElementById('mockTimeout2').value;
    var msg=document.getElementById('mockMsg2').value;
    var header=document.getElementById('mockHeader2').value;
    var callbackURL=document.getElementById('callbackURL2').value;
    var callbackType=document.getElementById('callbackType2').value;
    var callbackPara=document.getElementById('callbackPara2').value;
    var reg=/((([A-Za-z]{3,9}:(?:\/\/)?)(?:[\-;:&=\+\$,\w]+@)?[A-Za-z0-9\.\-]+|(?:www\.|[\-;:&=\+\$,\w]+@)[A-Za-z0-9\.\-]+)((?:\/[\+~%\/\.\w\-_]*)?\??(?:[\-\+=&;%@\.\w_]*)#?(?:[\.\!\/\\\w]*))?)/;
    if(caseName.length==0||msg.length==0||code.length==0||time.length==0||(callbackURL.length>0&&callbackType.length==0)){
        alert("必填项不能为空！");
    }else if(callbackURL.length!=0&&!reg.test(callbackURL)){
        alert("url必须是以http://或https://开头的合法网址！");
    }else{
        var data = {'detailid': detailid, 'caseName': caseName, 'code': code, 'time': time, 'msg': msg, 'header': header, 'callbackURL': callbackURL, 'callbackType': callbackType, 'callbackPara': callbackPara};
        $.post(modifySubmiturl, data, function (list1) {
            if (list1.indexOf("1") > -1) {
                alert("修改失败！mock返回值或mockHeader不符合Json格式！");
            } else {
                alert("修改成功！");
                window.close();
            }
        })
    }
}
/*
function modifyCondition(conIdList,i) {
    var conId=conIdList.get(i);
    var conS=document.getElementById(i).value;
    var url=urlprefix+"/mocker/MockConditionSetting?type=modify&detailId=0&conditionId="+conId+"&conditionS="+conS;
    $.get(url,function (list1) {
        if(list1.indexOf("1")>-1){
            alert("修改失败！条件的格式必须为：A=B");
        }else if(list1.indexOf("2")>-1){
            alert("修改失败！已有mock场景设置了此条件");
        }else{
            alert("修改成功！");
            window.close();
        }
    })
}*/
function modifyCondition(conIdList,i) {
    var conId=conIdList.get(i);
    var conS=document.getElementById(i).value;
    var data = {'type': "modify", 'detailId': 0, 'conditionId': conId, 'conditionS': conS};
    var url=urlprefix+"/mocker/MockConditionSetting";
    $.post(url,data,function (list1) {
        if(list1.indexOf("1")>-1){
            alert("修改失败！条件的格式必须为：A=B");
        }else if(list1.indexOf("2")>-1){
            alert("修改失败！已有mock场景设置了此条件");
        }else{
            alert("修改成功！");
            window.close();
        }
    })
}

function deleteCondition(conIdList,i) {
    var msg = "确认删除？";
    if (confirm(msg) == true) {
        var conId = conIdList.get(i).replace("\"", "");
        var data = {'type': "delete",'conditionId': conId};
        var url=urlprefix+"/mocker/MockConditionSetting";
        $.post(url, data,function (list1) {
            alert("提交成功！");
            window.close();
        })
        return true;
    }else
        {
            return false;
        }
    }

function newConditionPage(detailid) {
    var url="createCondition.html?detailId="+detailid;
    window.open(url);
}
function addCondition() {
    var loc = location.search;
    var loc1=loc.replace("?","");
    var locArray=loc1.split("&amp;");
    var detailId=locArray[0].split("=")[1];
    var conS=document.getElementById("mockCondition").value;
    var data = {'type': "add", 'detailId': detailId,'conditionS': conS};
    var url=urlprefix+"/mocker/MockConditionSetting";
    $.post(url,data,function (list1) {
        if(list1.indexOf("1")>-1){
            alert("创建失败！条件的格式必须为：A=B");
        }else if(list1.indexOf("2")>-1){
            alert("创建失败！已有mock场景设置了此条件");
        }else{
            alert("创建成功！");
            window.close();
        }
    })
}

function createDetailSubmit() {
    var mockCaseName=document.getElementById("mockCaseName").value;
    var mock_timeout=document.getElementById("mockTimeout").value;
    var mockCode=document.getElementById("mockCodeName").value;
    var mockResponseMsg=document.getElementById("mockResponseMsg").value;
    var mockResponseHeader=document.getElementById("mockResponseHeader").value;
    var callbackURL=document.getElementById("callbackURL").value;
    var callbackType=document.getElementById("callbackType").value;
    var callbackPara=document.getElementById("callbackPara").value;
    var name="";
    var APIName="";
    var reg=/((([A-Za-z]{3,9}:(?:\/\/)?)(?:[\-;:&=\+\$,\w]+@)?[A-Za-z0-9\.\-]+|(?:www\.|[\-;:&=\+\$,\w]+@)[A-Za-z0-9\.\-]+)((?:\/[\+~%\/\.\w\-_]*)?\??(?:[\-\+=&;%@\.\w_]*)#?(?:[\.\!\/\\\w]*))?)/;

    if(mockCaseName.length==0||mockResponseMsg.length==0||mock_timeout.length==0||mockCode.length==0){
        alert("必填项不能为空！");
    }else if(callbackURL.length!=0&&!reg.test(callbackURL)){
        alert("url必须是以http://或https://开头的合法网址！");
    }else{
        var url = location.search; //获取url中"?"符后的字串
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            name = decodeURI(strs[0].split("=")[1]);
            APIName = strs[1].split("=")[1];
        }

        var url = urlprefix+"/mocker/MockDetailCreate";
        var data = {
            "mockType": "返回值",
            "mockCaseName": mockCaseName,
            "mock_timeout": mock_timeout,
            "mockCode": mockCode,
            "mockResponseMsg": mockResponseMsg,
            "mockResponseHeader": mockResponseHeader,
            "callbackURL": callbackURL,
            "callbackType": callbackType,
            "callbackPara": callbackPara,
            "mockAPI": APIName,
            "name": name
        };
        $.post(url, data, function (list1) {
            if (list1.indexOf("1") > -1) {
                alert("创建失败！mock返回值不符合Json格式！");
            } else {
                alert("创建成功！");
                window.close();
            }
        })
    }
}

function createAuthorSubmit() {
    var department = document.getElementById("department").value;
    var name = document.getElementById("username").value;
    var url = urlprefix+"/mocker/MockAuthorCreate";
    if (department.length==0||name.length==0) {
        alert("必填项不能为空！");
    } else {
        var data = {"department": department, "name": name};
        $.post(url, data, function (list1) {
            if (list1.indexOf("0") > -1) {
                alert("创建成功！");
                window.close();
            } else {
                alert("该部门姓名已存在！");
            }
        })
}
}
function createAPISubmit(){
    var server=document.getElementById("server").value;
    var API=document.getElementById("API").value;
    var url=urlprefix+"/mocker/MockAPICreate";
    if (server.length==0||API.length==0) {
        alert("必填项不能为空！");
    } else {
        var data = {"server": server, "API": API};
        $.post(url, data, function (list1) {
            if (list1.indexOf("1") > -1) {
                alert("该接口已存在！");
            }
            if (list1.indexOf("2") > -1) {
                alert("接口必须以“/”开头！");
            }
            if (list1.indexOf("0") > -1) {
                alert("创建成功！");
                window.close();
            }
        })
    }

}