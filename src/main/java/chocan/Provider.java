package chocan;

public class Provider extends Member {

    public Provider() {

        super.id = 0;
        super.active = false;
        super.name = null;
        super.address = null;
        super.city = null;
        super.state = null;
        super.zip = 0;
        super.left = null;
        super.right = null;

    }

    public Provider GoLeft() {
        return null;
    }

    public Provider GoRight() {
        return null;
    }

    public Provider SetLeft(Provider left) {
        return null;
    }

    public Provider SetRight(Provider right) {
        return null;
    }

    public int CheckID(int id) {
        return 0;
    }

    public boolean Add() {
        return false;
    }

    public boolean Update() {
        return false;
    }

}
