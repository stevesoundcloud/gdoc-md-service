package gdocmd.gdrive;

public class GDriveFileReference {
  public final String id;
  public final String name;

  public GDriveFileReference(String id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GDriveFileReference that = (GDriveFileReference) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    return name != null ? name.equals(that.name) : that.name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "GDriveFileReference{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
}
