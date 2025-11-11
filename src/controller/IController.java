package controller;

import exceptions.MyException;
import exceptions.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;

public interface IController {
    public void executeOneStep() throws FileNotFoundException, RepoException, MyException;

    public void executeAllSteps() throws FileNotFoundException, RepoException, MyException;

    public void setDisplay(boolean display);

    public boolean getDisplay();

    public void addPrgState(PrgState prgState);

    public void clearPrgState();
}