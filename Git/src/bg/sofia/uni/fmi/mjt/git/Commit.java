package bg.sofia.uni.fmi.mjt.git;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

public class Commit {

    private String message;
    private String hash;
    private String commitTime;
    public HashSet<String> filesInCommit; //HashSet because they have only name and after they are
    // in the commit I dont't need their order

    public Commit(String message) {
        this.message = message;
        this.commitTime = getDate();
        this.hash = hexDigest(commitTime + message);
        filesInCommit = new HashSet<String>();
    }

    public String getHash() {
        return hash;
    }

    public String hexDigest(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return convertBytesToHex(bytes);
    }

    private String convertBytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte current : bytes) {
            hex.append(String.format("%02x", current));
        }

        return hex.toString();
    }

    private String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm yyyy", Locale.ENGLISH);

        now.format(formatter); // Thu Oct 25 11:13 2018
        return formatter.format(now);
    }


    public String getCommitTime() {
        return this.commitTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Commit clone() {
        Commit commit = new Commit(this.message);
        commit.hash = this.hash;
        commit.commitTime = this.commitTime;
        commit.filesInCommit = new HashSet<String>(this.filesInCommit);
        return commit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commit commit = (Commit) o;
        return Objects.equals(message, commit.message) &&
                Objects.equals(hash, commit.hash) &&
                Objects.equals(commitTime, commit.commitTime) &&
                Objects.equals(filesInCommit, commit.filesInCommit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, hash, commitTime, filesInCommit);
    }

}
