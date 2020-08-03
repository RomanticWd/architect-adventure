package site.lgong.framework.proxy;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 代理接口
 * @createTime 2020/8/3 22:34
 */
public interface Proxy {

    /**
     * @return Object
     * @description 执行链式代理
     * @date: 2020/8/3
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
