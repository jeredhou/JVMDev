package cn.com.cpic.jvm.s01;

import java.lang.reflect.Method;  

import net.sf.cglib.proxy.Enhancer;  
import net.sf.cglib.proxy.MethodInterceptor;  
import net.sf.cglib.proxy.MethodProxy;  

public class OOMTestForGCGlib {

    static class OOMObject {  
    }
    
    static class CglibProxy implements MethodInterceptor{
    	private Enhancer enhancer = new Enhancer();
    	
    	public Object getProxy(Class clazz){ 
	        enhancer.setSuperclass(OOMObject.class);  
	        enhancer.setUseCache(false);	        
	        enhancer.setCallback(this);
	        return enhancer.create();
    	}
		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				   MethodProxy proxy) throws Throwable {
			// TODO Auto-generated method stub
			return proxy.invoke(obj, args);
		}
    	
    }
  
    public static void main(String[] args) {
        while (true) {  
        	new CglibProxy().getProxy(OOMObject.class);
        }
    }
}
