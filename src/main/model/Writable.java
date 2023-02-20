package model;

@FunctionalInterface
public interface Writable<T> {
    // EFFECTS: returns this as some writable form (e.g. JSONObject, JSONArray).
    T jsonRepr();
}
