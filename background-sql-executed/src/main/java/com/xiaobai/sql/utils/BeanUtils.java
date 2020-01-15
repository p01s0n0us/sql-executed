package com.xiaobai.sql.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

/**
 * @author XinHuiChen
 */
@Slf4j
public class BeanUtils {

    private BeanUtils() {}

    public static void updateProperties(@NotNull Object source, @NotNull Object target) {
        Assert.notNull(source, "source object must not be null");
        Assert.notNull(target, "target object must not be null");

        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static <T> T transformFrom(@Nullable Object source, @NonNull Class<T> targetClass) {
        Assert.notNull(targetClass, "Target class must not be null");

        if (source == null) {
            return null;
        }

        T targetInstance = null;
        try {
            targetInstance = targetClass.getConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, targetInstance);
            return targetInstance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}