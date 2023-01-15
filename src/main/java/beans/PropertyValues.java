package beans;

import java.util.ArrayList;
import java.util.List;

/*
this class is used to pass through the property value info in classes
 */
public class PropertyValues {

    private List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv){
        propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String name){
        for(PropertyValue pv:propertyValueList){
            if(pv.getName().equals(name))
                return pv;
        }
        return null;
    }

}
