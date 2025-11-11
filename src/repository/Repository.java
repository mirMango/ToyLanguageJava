package repository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import exceptions.RepoException;
import model.PrgState;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private String fileName;
    public Repository(String fileName){
        this.fileName=fileName;
        List<PrgState> prgStates=new ArrayList<>();

    }

    @Override
    public PrgState getPrgState() {
        return prgStates.get(0);
    }

    public void addPrgState(PrgState prgState){
        prgStates.add(prgState);
    }

    @Override
    public void logPrgState() throws FileNotFoundException,RepoException{
        PrintWriter ps=new PrintWriter(fileName);
        BufferedWriter bw=new BufferedWriter(ps);

        try {
            bw.write(getPrgState().toString());
            bw.write("\n");
        }catch (IOException e){
            throw new RepoException("Scrierea nu a fost reusita");
        }finally {
            ps.close();
        }
    }

    @Override
    public void clearPrgState() {
        prgStates.clear();
    }
}