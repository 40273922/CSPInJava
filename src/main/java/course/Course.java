package course;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * @author Zhai Jinpei
 */
public class Course<K,V>
        extends HashMap<K ,V>{
    BiConsumer<K,V> b = (k,v)->System.out.println("id:" + k + ",course:" + v);
    Course<K,V> $(K k,V v){
        this.put(k,v);
        return this;
    }
    void like(@Nullable V l){
        this.forEach((k,v)->{
            if(l == null) b.accept(k,v);
            if(this.containsValue(l))
                if(v.equals(l)) b.accept(k,v);
        });
    }
}

