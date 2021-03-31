package prescriptioncontract;

import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PrescriptionContractTest {
    
    @Test
    public void invokeUnknownTransaction() {
        PrescriptionContract contract = new PrescriptionContract();
        Context ctx = mock(Context.class);

        Throwable thrown = catchThrowable(() -> {
            contract.unknownTransaction(ctx);
        });

        assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                .hasMessage("Undefined contract method called");
        assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo(null);

        verifyZeroInteractions(ctx);
    }

    @Nested
    class InvokePrescriptionExists {

        @Test
        public void whenPrescriptionExists() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"owner\":\"-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNHADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----\",\"date\":\"31-03-2021 12-27-00\",\"product\":\"ASACOLON\",\"quantity\":\"28\",\"productID\":\"G13072\",\"doseQuantity\":\"28\",\"pID\":\"001\",\"doseType\":\"TABS\",\"issuer\":\"-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNHADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----\",\"doseStrength\":\"400MG\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"comment\":\"\",\"productPackage\":\"\",\"status\":\"ACTIVE\"}");

            boolean response = contract.prescriptionExists(ctx, "001");

            assertEquals(response, true);
        }

        @Test
        public void whenPrescriptionDoesNotExist() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001")).thenReturn("");

            boolean response = contract.prescriptionExists(ctx, "001");
            assertEquals(response, false);

        }
    }

    @Nested
    class InvokeIssuePrescription {

        @Test
        public void whenPrescriptionExists() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("694061954"))
                    .thenReturn("{\"owner\":\"owner\",\"date\":\"31-03-2021 12-27-00\",\"product\":\"ASACOLON\",\"quantity\":\"28\",\"productID\":\"G13072\",\"doseQuantity\":\"28\",\"pID\":\"001\",\"doseType\":\"TABS\",\"issuer\":\"-----BEGINCERTIFICATE-----MIICgzCCAiqgAwIBAgIUM5+j+ONg63n6zkHn4uAK9rIqGRswCgYIKoZIzj0EAwIwcDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQHEwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2NhLm9yZzEuZXhhbXBsZS5jb20wHhcNMjEwMjEyMTk1NzAwWhcNMjIwMjEyMjAwMjAwWjBEMTAwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMBIGA1UECxMLZGVwYXJ0bWVudDExEDAOBgNVBAMTB2FwcFVzZXIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAASJHI1gh/i7Z8TiCS/9jldWH/EZ0aj9mdyo7gB20xQ2eANllCRNRKaPtjVYqcZCi6LPiz/UuMZssYR83tZYmknEo4HNMIHKMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQiP1+bIKJXSN8wXEEtCy4Kd2C6GzAfBgNVHSMEGDAWgBQm/W4Zql66TuJXCEcO9b7sLnJ/CjBqBggqAwQFBgcIAQReeyJhdHRycyI6eyJoZi5BZmZpbGlhdGlvbiI6Im9yZzEuZGVwYXJ0bWVudDEiLCJoZi5FbnJvbGxtZW50SUQiOiJhcHBVc2VyIiwiaGYuVHlwZSI6ImNsaWVudCJ9fTAKBggqhkjOPQQDAgNHADBEAiAaOwsAAVxtwzAfrYITK1TBqMz6EpWO7adbr0OZa8OjUgIgNiW8974VpeXys2CIrHYF85fIOXQ8A4/RNTOy2EdqMlg=-----ENDCERTIFICATE-----\",\"doseStrength\":\"400MG\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"comment\":\"\",\"productPackage\":\"\",\"status\":\"ACTIVE\"}");

            String response = contract.issue(ctx, "31-03-2021 12-27-00", "owner", "ASACOLON", "G13072", "", "28", "400MG", "TABS", "28", "TAKE TWO TWICE DAILY", "");
            System.out.println(response);
            assertEquals(response, "");
        }

        @Test
        public void whenPrescriptionDoesNotExist() {
            String prescription = "{\"comment\":\"\",\"date\":\"31-03-2021 12-27-00\",\"doseQuantity\":\"28\",\"doseStrength\":\"400MG\",\"doseType\":\"TABS\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"issuer\":\"0\",\"owner\":\"0\",\"pID\":\"1748839871\",\"product\":\"ASACOLON\",\"productID\":\"G13072\",\"productPackage\":\"\",\"quantity\":\"28\",\"status\":\"ACTIVE\"}";
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("asset1")).thenReturn("");

            String response = contract.issue(ctx, "31-03-2021 12-27-00", "0", "ASACOLON", "G13072", "", "28", "400MG", "TABS", "28", "TAKE TWO TWICE DAILY", "");

            assertThat(prescription).isEqualTo(response);
        }
    }

    @Nested
    class TransferPrescriptionTransaction {

        @Test
        public void whenPrescriptionExists() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"owner\":\"owner\",\"date\":\"31-03-2021 12-27-00\",\"product\":\"ASACOLON\",\"quantity\":\"28\",\"productID\":\"G13072\",\"doseQuantity\":\"28\",\"pID\":\"001\",\"doseType\":\"TABS\",\"issuer\":\"owner\",\"doseStrength\":\"400MG\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"comment\":\"\",\"productPackage\":\"\",\"status\":\"ACTIVE\"}");

            String response = contract.transferPrescription(ctx, "001", "owner", "AnotherUser");

            assertThat(response).isEqualTo("{\"comment\":\"\",\"date\":\"31-03-2021 12-27-00\",\"doseQuantity\":\"28\",\"doseStrength\":\"400MG\",\"doseType\":\"TABS\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"issuer\":\"owner\",\"owner\":\"AnotherUser\",\"pID\":\"001\",\"product\":\"ASACOLON\",\"productID\":\"G13072\",\"productPackage\":\"\",\"quantity\":\"28\",\"status\":\"ACTIVE\"}");
        }

        @Test
        public void whenPrescriptionDoesNotExist() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001")).thenReturn(null);

            String response = contract.transferPrescription(ctx, "001", "owner", "AnotherUser");


            assertThat(response).isEqualTo(null);

        }
    }

    @Nested
    class ChangeStatusTransaction {

        @Test
        public void whenPrescriptionExists() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"owner\":\"owner\",\"date\":\"31-03-2021 12-27-00\",\"product\":\"ASACOLON\",\"quantity\":\"28\",\"productID\":\"G13072\",\"doseQuantity\":\"28\",\"pID\":\"001\",\"doseType\":\"TABS\",\"issuer\":\"owner\",\"doseStrength\":\"400MG\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"comment\":\"\",\"productPackage\":\"\",\"status\":\"ACTIVE\"}");

            String response = contract.changeStatus(ctx, "001", "REDEEMED");

            assertThat(response).isEqualTo("{\"comment\":\"\",\"date\":\"31-03-2021 12-27-00\",\"doseQuantity\":\"28\",\"doseStrength\":\"400MG\",\"doseType\":\"TABS\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"issuer\":\"owner\",\"owner\":\"owner\",\"pID\":\"001\",\"product\":\"ASACOLON\",\"productID\":\"G13072\",\"productPackage\":\"\",\"quantity\":\"28\",\"status\":\"REDEEMED\"}");
        }

        @Test
        public void whenPrescriptionDoesNotExist() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001")).thenReturn(null);

            String response = contract.changeStatus(ctx, "001", "REDEEMED");


            assertThat(response).isEqualTo(null);

        }
    }

    @Nested
    class UpdatePrescriptionTransaction {

        @Test
        public void whenPrescriptionExists() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"owner\":\"owner\",\"date\":\"31-03-2021 12-27-00\",\"product\":\"ASACOLON\",\"quantity\":\"28\",\"productID\":\"G13072\",\"doseQuantity\":\"28\",\"pID\":\"001\",\"doseType\":\"TABS\",\"issuer\":\"owner\",\"doseStrength\":\"400MG\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"comment\":\"\",\"productPackage\":\"\",\"status\":\"ACTIVE\"}");

            String response = contract.updatePrescription(ctx, "001", "31-03-2021 12-27-00", "owner", "owner", "ASACOLON", "G13072", "", "28", "400MG", "TABS", "28", "TAKE TWO TWICE DAILY", "ENJOY!", "ACTIVE");

            assertThat(response).isEqualTo("{\"comment\":\"ENJOY!\",\"date\":\"31-03-2021 12-27-00\",\"doseQuantity\":\"28\",\"doseStrength\":\"400MG\",\"doseType\":\"TABS\",\"instruction\":\"TAKE TWO TWICE DAILY\",\"issuer\":\"owner\",\"owner\":\"owner\",\"pID\":\"001\",\"product\":\"ASACOLON\",\"productID\":\"G13072\",\"productPackage\":\"\",\"quantity\":\"28\",\"status\":\"ACTIVE\"}");
        }

        @Test
        public void whenPrescriptionDoesNotExist() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001")).thenReturn(null);
            String response = contract.updatePrescription(ctx, "001", "31-03-2021 12-27-00", "owner", "owner", "ASACOLON", "G13072", "", "28", "400MG", "TABS", "28", "TAKE TWO TWICE DAILY", "ENJOY!", "ACTIVE");
            assertThat(response).isEqualTo("");
        }
    }

    @Nested
    class GetHistoryTransaction{

        @Test
        public void whenPrescriptionDoesNotExist() {
            PrescriptionContract contract = new PrescriptionContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001")).thenReturn(null);

            String response = contract.getHistoryForKey(ctx, "001");

            assertThat(response).isEqualTo(null);

        }
    }
}
