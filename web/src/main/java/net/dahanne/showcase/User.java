package net.dahanne.showcase;

/**
 * Created by anthony on 2014-10-19.
 */
public class User {
  private String name;
  private long id;

  public User() {
  }

  public User(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    if (!name.equals(user.name)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", id=" + id +
        '}';
  }
}
