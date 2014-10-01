package cn.com.cpic.jvm.s01;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class OOMTestForJDK7 {
	interface IOOMObject{
		public void printName();
		
	}

	static class OOMObject implements IOOMObject{
		private String name = "test";

		public void printName() {
			System.out.println("name="+name);
		}
	}
	
	static class DynamicProxyHandler implements InvocationHandler{
		private Object obj;
		public DynamicProxyHandler(Object object){
			this.obj = object;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			return method.invoke(obj, args);
		}		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ClassLoader> list = new ArrayList<ClassLoader>();
		List<IOOMObject> oomList = new ArrayList<IOOMObject>();
		try{
			while(true){
				OOMObject object = new OOMObject();
				DynamicProxyHandler handler = new DynamicProxyHandler(object);
				ClassLoader cl = IOOMObject.class.getClassLoader();
				IOOMObject obj = (IOOMObject)Proxy.newProxyInstance(cl, new Class[]{IOOMObject.class}, handler);
				list.add(cl);
				//obj.printName();
				oomList.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

