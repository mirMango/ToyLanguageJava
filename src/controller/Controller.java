package controller;

import exceptions.MyException;
import exceptions.RepoException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import repository.IRepository;

import java.io.FileNotFoundException;

public class Controller implements IController {
    private IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeOneStep() throws FileNotFoundException, RepoException, MyException {
        MyIStack<IStmt> exeStack = repository.getPrgState().getExeStack();
        IStmt stmt = exeStack.pop();
        stmt.execute(repository.getPrgState());
        repository.logPrgState();
    }

    @Override
    public void executeAllSteps() throws FileNotFoundException, RepoException, MyException {
        MyIStack<IStmt> exeStack = repository.getPrgState().getExeStack();
        while(!exeStack.isEmpty()) {
            executeOneStep();
        }
    }

    @Override
    public void setDisplay(boolean display) {
        this.displayFlag = display;
    }

    @Override
    public boolean getDisplay() {
        return displayFlag;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        repository.addPrgState(prgState);
    }

    @Override
    public void clearPrgState() {
        repository.clearPrgState();
    }
}