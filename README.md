# 项目描述
<p>
	用于spring boot 2.X版本的异常处理增强
</p>
<p>
	使用后创建相关的自定义异常和对应自定义异常的处理类（Handler）,在项目抛出相关自定义异常后，会被统一异常处理类（CommonExceptionHandler）捕获，并根据异常的类型查询对应的处理类（Handler）
 </p>
 <p>
  	提供主要用于API接口层面的异常处理类型和页面层面的异常处理类型
</p>
 <p>
    同时该工具类提供异常消息文本国际化的处理
</p>

# 使用方式

## 自定义异常拦截（API 异常）

添加一个自定义的异常，继承于CommonApiException

    public class TestApiCommonException extends CommonApiException {
        public TestApiCommonApiException(String errorCode, String errorMsg, String langType) {
            super(errorCode, errorMsg, langType);
        }
    }

添加一个异常处理类，继承于CommonApiExceptionHandler<你的自定义异常，你的异常返回值（该结果将直接输出给调用方）>，并添加上Srping 框架的@Service（工具通过spring的机制获取bean管理器内所有继承于CommonApiExceptionHandler的bean）


    @Service
    public class TestApiCommonExceptionHandler implements CommonApiExceptionHandler<TestApiCommonException, JSONObject> {
    
        @Override
        public JSONObject handler(TestApiCommonException e, HttpServletRequest request, HttpServletResponse response) {
    
            JSONObject jsonObject = new JSONObject();
    
            jsonObject.put("resultCode", CommonExceptionStatus.STATUS_ERROR);
            jsonObject.put("resultMsg", e.getMessage());
            jsonObject.put("data", e.getClass() + ":" + e.getErrorMsg());
    
            return jsonObject;
        }
    }

添加一个测试的controller，抛出TestApiCommonException 异常

    @GetMapping("/1")
    private String test1() {
        throw new TestApiCommonException("1", "test.index", "en_us");
    }

调用后，抛出异常后会被拦截，然后根据TestApiCommonExceptionHandler内的handler方法处理后，将对应的返回值直接输出


## 自定义异常拦截（页面 异常）

添加一个自定义的异常，CommonViewException

    public class TestViewCommonException extends CommonViewException {
        public TestViewCommonApiException(String errorCode, String errorMsg, String langType) {
            super(errorCode, errorMsg, langType);
        }
    }

添加一个异常处理类，CommonViewExceptionHandler<你的自定义异常，你的异常返回值（该结果将直接输出给调用方）>，并添加上Srping 框架的@Service（工具通过spring的机制获取bean管理器内所有继承于CommonViewExceptionHandler的bean）

    @Service
    public class TestViewCommonExceptionHandler implements CommonViewExceptionHandler<TestViewCommonException> {
        @Override
        public void handler(TestViewCommonException e, HttpServletRequest request, HttpServletResponse response) {
            try {
                response.getWriter().write("Jump to the other page .");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

添加一个测试的controller，抛出TestViewCommonException 异常

    @GetMapping("/2")
    private String test2() {
        throw new TestViewCommonException("1", "test.index2", "en_us");
    }

调用后，抛出异常后会被拦截，然后根据TestViewCommonExceptionHandler内的handler方法处理后，将对应的返回值直接输出

# 异常消息文本国际化

<p>配置国际文本的数据源 </p>
<p>0=默认模式，内存不进行国际化处理，原样返回 </p>
<p>1=内存模式 </p>
<p>2=配置文件模式</p>

    exception.handler.profile-type=0

是否开启异常消息文本国际化功能，为false时不进行国际化文本替换，直接原样输出

    exception.handler.is-need-internationalization=true

## 国际化文本放置于内存内

当 exception.handler.profile-type=1时，使用内存数据源
在项目启动后，通过 @Autowired 注入InMemoryInternationalizationDatasource ,并调用putValue方法往内存添加异常消息文本

    public void putValue(String index, String value, String lang) {
        logger.info("Put profile value [ key = {} , value = {} ] to profile key [ {} ]. ", index, value, lang);
        Map<String, String> contextMap = internationalizationProfileMap.computeIfAbsent(lang, k -> new ConcurrentHashMap<>());

        contextMap.put(index, value);
    }

其中lang指国际化的语言类型，例如 en_us,zh_cn之类，index 是该国际化的key，value对应是国际化的文本内容

## 国际化文本放置于配置文件内（exception-i18n.json）
在spring boot 项目的resources 下创建exception-i18n.json
内容格式为

    {
        "语言类型": {
            "key": "value"
        }
    }

例如

    {
        "en_us": {
            "test.index": "valueAAA"
        },
        "zn_ch": {
            "test.index": "valueBBB"
        }
    }

<p>
    当抛出异常为 throw new TestApiCommonException("1", "test.index", "en_us") ，最终test.index输出 valueAAA
</p>
