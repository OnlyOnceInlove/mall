package cn.pxkeji.core;

import cn.pxkeji.core.ModuleInitializeContext;

/**
 * 模块初始化抽象类
 * 每个模块要求有一个实现类，主要完成模块的一些初始化工作，如数据库初始化
 * 程序启动时
 */
public abstract class ModuleInitializer{
    public abstract void init(ModuleInitializeContext context);
    public abstract boolean check(ModuleInitializeContext context);

    /**
     * 从特定存储中获取模块初始化的上下文信息，比如从数据库中，每个模块都有一个唯一的key
     */
    private ModuleInitializeContext getContext(){
        return null;
    }
}