package infocontract;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void isReflexive() {
        User user = new User("3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");

        assertThat(user).isEqualTo(user);
    }

    @Test
    public void handlesInequality() {
        User userA = new User("3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");
        User userB = new User("7345216J", "Mr", "Greg", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWdj4kgl91/cvaDDScvHJKDEkdlpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");

        assertThat(userA).isNotEqualTo(userB);
    }

    @Test
    public void handlesOtherObjects() {
        User userA = new User("3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");
        String userB = "not a user";

        assertThat(userA).isNotEqualTo(userB);
    }

    @Test
    public void handlesNull() {
        User user = new User("3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");

        assertThat(user).isNotEqualTo(null);
    }

    @Test
    public void handlesDeserialization(){
        User user = new User("3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");

        JSONObject jsonUser = new JSONObject();
        jsonUser.put("identifier", "3324784V");
        jsonUser.put("title", "Mr");
        jsonUser.put("firstname", "Dan");
        jsonUser.put("surname", "Simons");
        jsonUser.put("address", "52 Strand Street Skerries Dublin");
        jsonUser.put("dob", "01-01-1999");
        jsonUser.put("gender", "M");
        jsonUser.put("email", "sanieldimons@gmail.com");
        jsonUser.put("status", "M");
        jsonUser.put("cert", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");

        User stringToUser = User.deserialize(jsonUser.toString());

        assertThat(user).isEqualToComparingFieldByField(stringToUser);
    }

    @Test
    public void handlesSerialization(){
        User user = new User("3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNH\nADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----");

        String userString = new String (User.serialize(user));
        JSONObject stringToJson = new JSONObject(userString);
        System.out.println(stringToJson.toString());
        assertEquals(user.getIdentifier(), stringToJson.get("identifier"));
        assertEquals(user.getTitle(), stringToJson.get("title"));
        assertEquals(user.getFirstname(), stringToJson.get("firstname"));
        assertEquals(user.getSurname(), stringToJson.get("surname"));
        assertEquals(user.getAddress(), stringToJson.get("address"));
        assertEquals(user.getDob(), stringToJson.get("dob"));
        assertEquals(user.getGender(), stringToJson.get("gender"));
        assertEquals(user.getEmail(), stringToJson.get("email"));
        assertEquals(user.getStatus(), stringToJson.get("status"));
        assertEquals(user.getCert(), stringToJson.get("cert"));
    }
}
