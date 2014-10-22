
EventUtil=function EventUtil(){};

/**
 * ȡ�����¼��Ķ���
 * @param event �¼��塣
 */
EventUtil.eventCurrentTarget=function(event)
{
  // Internet Explorer
  if (event.srcElement)
    return event.srcElement;
  // W3C DOM
  if (event.currentTarget)
    return event.currentTarget;
};


/**
 * ȡ�����¼��Ķ���
 * @param event �¼��塣
 */
EventUtil.eventTarget=function(event)
{
  // Internet Explorer
  if (event.srcElement)
    return event.srcElement;
  // W3C DOM
  if (event.target)
    return event.target;
};



/**
 * ��̬�����¼���
 * @param obj element object��
 * @param eventType The event type for which the user is registering.
 * @param aEventUtil.listener of type EventListener. 
 * @param isCapture useCapture of type boolean.
 */
EventUtil.addEvent=function(obj, eventType, afunction, isCapture) 
{
  // W3C DOM
  if (obj.addEventListener) {
     obj.addEventListener(eventType, afunction, isCapture);
     return true;
  }
  // Internet Explorer
  else if (obj.attachEvent) {
     return obj.attachEvent("on"+eventType, afunction);
  }
  else
    return false;
};

/**
 * ��̬�����¼���
 * @param obj element object��
 * @param eventType The event type for which the user is registering.
 * @param aEventUtil.listener of type EventListener. 
 * @param isCapture useCapture of type boolean.
 */
EventUtil.removeEvent=function(obj, eventType, afunction, isCapture)
{
  if (obj.removeEventListener) {
    obj.removeEventListener(eventType, afunction, isCapture);
    return true;
  }
  else if (obj.detachEvent) {
     return obj.detachEvent("on"+eventType, afunction);
  }
  else
    return false;
};



/**
 * ����ָ�����¼���
 * @param elem ����
 * @eventName �¼�����
 */
EventUtil.fireEvent=function(elem,eventName)
{
  if(!elem || !eventName)
    return;
  try
  {
    if(elem.fireEvent(eventName))//ie is ok,firefox isn't ok
      return;
  }
  catch(e)
  {
    if(eventName.toLowerCase()=="onchange" && elem.onchange)
    {
      elem.onchange();
      return;
    }
    if(eventName.toLowerCase()=="onclick" && elem.onclick)
    {
      elem.onclick();
      return;
    }
    var eventBodyObj=elem.getAttribute(eventName);
    if(!eventBodyObj)
      return;
    var eventBody=eventBodyObj.toString();
    if(eventBody=="undefined")
      return;
    var i=eventBody.indexOf("{");
    if(i>-1)
      eventBody=eventBody.substring(i+1);
    i=eventBody.lastIndexOf("}");
    if(i>-1)
      eventBody=eventBody.substring(0,i);
    eventBody=eventBody.replace(/document/ig,"elem.document");
    eval(eventBody); 
  }   
};

