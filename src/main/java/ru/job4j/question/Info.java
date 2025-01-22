package ru.job4j.question;

import java.util.Objects;

public class Info {
    private final int added;
    private final int changed;
    private final int deleted;

    public Info(int added, int changed, int deleted) {
        this.added = added;
        this.changed = changed;
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Info info = (Info) object;
        return added == info.added && changed == info.changed && deleted == info.deleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(added, changed, deleted);
    }

}
