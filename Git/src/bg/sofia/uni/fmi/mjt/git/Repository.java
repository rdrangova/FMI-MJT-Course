package bg.sofia.uni.fmi.mjt.git;

import java.util.*;


public class Repository {

    private HashMap<String, Branch> repo;
    private HashSet<String> stage = new HashSet<String>();
    private HashSet<String> removeStage = new HashSet<>();
    private Branch currentBranch;
    private int changesCounter = 0;

    public Repository() {
        this.repo = new HashMap<>();
        Branch master = new Branch("master");
        repo.put(master.getBranch(), master);
        currentBranch = repo.get("master");
    }


    public Result add(String... files) { //need help about this
        for (String file : files) {
            if (stage.contains(file)) {
                return new Result(false, "'" + file + "' already exists");
            }
            for (Commit commit : currentBranch.commits.values()) {
                if (commit.filesInCommit.contains(file)) {
                    return new Result(false, "'" + file + "' already exists");
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (String file : files) {
            if (removeStage.contains(file)) {
                changesCounter--;
                removeStage.remove(file);
            }

            stage.add(file);
            output.append(file).append(", ");
            changesCounter++;
        }
        output.deleteCharAt(output.length() - 2);

        return new Result(true, "added " + output + "to stage");
    }

    public Result commit(String commitMessage) {
        if (changesCounter == 0) {
            return new Result(false, "nothing to commit, working tree clean");
        }
        //int counter = stage.size()+removeStage.size();
        currentBranch.put(commitMessage, stage);
        currentBranch.setLastCommit(commitMessage);
        stage.clear();
        removeStage.clear();
        int counter = changesCounter;
        changesCounter = 0;
        return new Result(true, counter + " files changed");
    }

    public Result remove(String... files) {
        fileIterator:
        for (String file : files) {
            if (stage.contains(file)) {
                continue fileIterator;
            }
            for (Commit commit : currentBranch.commits.values()) {
                if (commit.filesInCommit.contains(file)) {
                    continue fileIterator;
                }
            }
            return new Result(false, "'" + file + "' did not match any files");
        }

        for (String file : files) {
            if (stage.remove(file))
                changesCounter--;
        }
        for (Commit commit : currentBranch.commits.values()) {
            for (String file : files) {
                if (commit.filesInCommit.remove(file))
                    changesCounter++;
            }
        }

        StringBuilder output = new StringBuilder();
        for (String file : files) {
            removeStage.add(file);
            output.append(file).append(", ");
        }
        output.deleteCharAt(output.length() - 2);
        return new Result(true, "added " + output + "for removal");
    }

    public Commit getHead() {
        return currentBranch.getLastCommit();
    }

    public Result log() {
        if (currentBranch.commits.size() == 0) {
            return new Result(false, "branch " + currentBranch.getBranch() + " does not have any commits yet");
        }

        StringBuilder fullMessage = new StringBuilder();

        List<String> alKeys = new ArrayList<String>(currentBranch.commits.keySet());

        // reverse order of keys
        Collections.reverse(alKeys);

        // iterate LHM using reverse order of keys
        for (String strKey : alKeys) {
            fullMessage.append("commit ")
                    .append(currentBranch.commits.get(strKey).getHash())
                    .append("\nDate: ")
                    .append(currentBranch.commits.get(strKey).getCommitTime())
                    .append("\n\n\t")
                    .append(currentBranch.commits.get(strKey).getMessage())
                    .append("\n\n");

        }

        return new Result(true, fullMessage.toString().substring(0, fullMessage.length() - 2));
    }

    public String getBranch() {
        return currentBranch.getBranch();
    }

    public Result createBranch(String name) {
        if (repo.containsKey(name)) {
            return new Result(false, "branch " + name + " already exists");
        }
        repo.put(name, new Branch(name));
        repo.get(name).copyCommits(currentBranch);
        if (currentBranch.getLastCommit() != null)
            repo.get(name).setLastCommit(currentBranch.getLastCommit().getMessage());
        return new Result(true, "created branch " + name);
    }

    public Result checkoutBranch(String name) {
        if (!repo.containsKey(name)) {
            return new Result(false, "branch " + name + " does not exist");
        }
        currentBranch = repo.get(name);
        return new Result(true, "switched to branch " + name);
    }

    public Result checkoutCommit(String hash) {

        if (!currentBranch.commits.containsKey(hash)) {
            return new Result(false, "commit " + hash + " does not exist");
        }
        if (currentBranch.getLastCommit() == null) {
            return new Result(false, "commit " + hash + " does not exist");
        }
        currentBranch.setLastCommit(currentBranch.commits.get(hash).getMessage());

        boolean destroyer=false;

        HashSet <Commit> toRemove = new HashSet<>();

        for (Map.Entry<String, Commit> entry : currentBranch.commits.entrySet()) {
            if (entry.getKey().equals(hash)) {
                destroyer = true;
            }
            if (destroyer) {
                toRemove.add(currentBranch.commits.get(entry.getKey()));
            }
        }
        currentBranch.commits.values().removeAll(toRemove);
        return new Result(true, "HEAD is now at " + hash);

    }


}
