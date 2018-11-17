package bg.sofia.uni.fmi.mjt.git;

public class Main {

    public static void main(String[] args){
        Repository repo;
        repo = new Repository();

        System.out.println(repo.add("src/Main.java","Radi", "Kola", "Apartament").getMessage());
        System.out.println(repo.remove("src/Main.java").getMessage());
        System.out.println(repo.commit("Add Main.java, tanya").getMessage());
        System.out.println(repo.log().getMessage());
        System.out.println(repo.remove("apartament").getMessage());
        System.out.println(repo.remove("Apartament").getMessage());
        System.out.println(repo.remove("Apartament").getMessage());
        System.out.println(repo.add("Apartament").getMessage());
        System.out.println(repo.commit("don't know what is happenning").getMessage());
        System.out.println(repo.log().getMessage());
        System.out.println(repo.add("Radi","Ferari","Kola").getMessage());
        System.out.println(repo.getHead().getMessage());
        System.out.println(repo.add("Radost","Will","Finish","Java").getMessage());
        System.out.println(repo.commit("First commit").getMessage());
        System.out.println(repo.log().getMessage());
        System.out.println(repo.createBranch("new").getMessage());
        System.out.println(repo.checkoutBranch("new").getMessage());
        System.out.println(repo.getHead().getMessage());
        System.out.println(repo.add("Joro","Pesho","Gosho").getMessage());
        System.out.println(repo.getBranch());
        System.out.println(repo.commit("This commit").getMessage());
        System.out.println(repo.getHead().getMessage());
        System.out.println(repo.checkoutBranch("master").getMessage());
        System.out.println(repo.getHead().getMessage());
        System.out.println(repo.checkoutCommit("9c3e8876f0efe510fe69d606157fd62ebd947900").getMessage());








//        System.out.println(repo.commit("Remove Main.java").getMessage());
//        System.out.println(repo.remove("tanya").getMessage());
//        System.out.println(repo.remove("tanya").getMessage());
//        System.out.println(repo.commit("hope repo is empty").getMessage());
//        System.out.println(repo.log().getMessage());
//        System.out.println(repo.getHead().getMessage());

//        System.out.println(repo.commit("Add Main.java").getMessage());
//        //System.out.println(repo.remove("src/Main.java").getMessage());
//        System.out.println(repo.createBranch("dev").getMessage());
//        System.out.println(repo.checkoutBranch("dev").getMessage());
//        System.out.println(repo.commit("Remove Main.java").getMessage());
//        System.out.println(repo.checkoutBranch("master").getMessage());
//        System.out.println(repo.commit("Please work").getMessage());
//        System.out.println(repo.remove("Radost").getMessage());
//        System.out.println(repo.remove("Fail").getMessage());
//        System.out.println(repo.commit("Please delete my name").getMessage());
//        System.out.println(repo.createBranch("bigBoss").getMessage());
//        System.out.println(repo.createBranch("bigBoss").getMessage());
//        System.out.println(repo.getHead().getMessage());
//        System.out.println(repo.log().getMessage());
//        System.out.println(repo.checkoutBranch("bigBoss").getMessage());
//        System.out.println(repo.remove("Will").getMessage());
//        System.out.println(repo.remove("Finnish").getMessage());
//        System.out.println(repo.getHead().getMessage());







        //assertSuccess("switched to branch master", actual);
        //assertEquals("Add Main.java", repo.getHead().getMessage());
    }
}
