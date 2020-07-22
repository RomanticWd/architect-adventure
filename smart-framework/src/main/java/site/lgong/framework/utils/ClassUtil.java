package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 类操作工具类，比如获取类加载器、加载类、获取指定包名下的所有类等
 * @createTime 2020/7/21 23:23
 */
@Slf4j
public final class ClassUtil {

    private ClassUtil() {
        log.error("工具类无法实例化");
    }

    /**
     * @return ClassLoader 类加载器
     * @description 获取类加载器
     * @date: 2020/7/21
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * @param isInitialized 是否初始化，即是否执行类的静态代码块,设置为false可以提高类加载速度
     * @return Class
     * @description 加载类
     * @date: 2020/7/21
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("加载类失败", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取某个包名下的所有类
     * @date: 2020/7/21
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    //获取协议并进行判断
                    String protocol = url.getProtocol();
                    if (StringUtils.equals(protocol, "file")) {
                        //替换所有空格
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packagePath, packageName);
                    } else if (StringUtils.equals(protocol, "jar")) {
                        //如果是jar包则要解析jar包中的.class文件并加载
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("获取类集合失败", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            //如果是文件
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.indexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    //得到类名
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                //不是文件，递归获取文件
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(subPackageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
