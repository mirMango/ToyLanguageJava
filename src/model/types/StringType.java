package model.types;

public class StringType implements IType{
    public StringType() {}
    public String toString(){
        return "String";
    }
    public boolean equals(Object another){
        return another instanceof IntType;
    }
}