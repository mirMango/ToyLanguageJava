package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public interface IExp {
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException;

}