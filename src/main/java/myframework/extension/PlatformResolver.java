package myframework.extension;

import com.codeborne.selenide.Selenide;
import myframework.util.PageAppContextUtil;

import java.util.Map;

public class PlatformResolver {

  public static <T> T page(Class<T> pageObjectClass) {
    T bean = null;
    Map<String, T> beansOfType = PageAppContextUtil.getContext().getBeansOfType(pageObjectClass);

    String pageObjectClassSimpleName = pageObjectClass.getSimpleName();
    for (String key : beansOfType.keySet()) {
      T curBean = beansOfType.get(key);
      String beanSimpleName = curBean.getClass().getSimpleName();
      beanSimpleName = beanSimpleName.substring(0, beanSimpleName.indexOf("$"));
      if (pageObjectClassSimpleName.contains(beanSimpleName)) {
        bean = curBean;
        break;
      }
    }

    if (bean == null) {
      throw new RuntimeException(
          pageObjectClassSimpleName + " - page not found. Is it marked by @Component?");
    }

    return Selenide.page(bean);
  }
}
