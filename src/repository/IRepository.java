package repository;

import exceptions.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;

public interface IRepository {
    public PrgState getPrgState();
    public void addPrgState(PrgState prgState);
    public void logPrgState() throws FileNotFoundException, RepoException;
    public void clearPrgState();
}