package persistence;

import org.json.JSONObject;

@FunctionalInterface
public interface Writable {
    // EFFECTS: returns this in a writable data form.
    JSONObject jsonRepr();
}
