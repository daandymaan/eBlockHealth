package infocontract;
import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
public class UserContractTest {

    @Test
    public void invokeUnknownTransaction() {
        UserContract contract = new UserContract();
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
    class InvokeUserExistsTransaction{
        @Test
        public void whenUserExists() {
            UserContract contract = new UserContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("3324784V"))
                    .thenReturn("{ \"identifier\": \"3324784V\", \"title\": \"Mr\", \"firstname\": \"Dan\", \"surname\": \"Simons\", \"address\": \"52 Strand Street Skerries Dublin\", \"dob\": \"01-01-1999\",\"gender\": \"M\", \"email\": \"sanieldimons@gmail.com\", \"status\": \"M\",\"cert\": \"cert1\" }");

            boolean response = contract.userExists(ctx, "3324784V");

            assertEquals(response, true);
        }

        @Test
        public void whenUserDoesNotExist() {
            UserContract contract = new UserContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("3324784V")).thenReturn("");

            boolean response = contract.userExists(ctx, "3324784V");
            assertEquals(response, false);

        }
    }

    @Nested
    class InvokeCreateUserTransaction{
        @Test
        public void whenUserExists() {
            UserContract contract = new UserContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("3324784V"))
                    .thenReturn("{ \"identifier\": \"3324784V\", \"title\": \"Mr\", \"firstname\": \"Dan\", \"surname\": \"Simons\", \"address\": \"52 Strand Street Skerries Dublin\", \"dob\": \"01-01-1999\",\"gender\": \"M\", \"email\": \"sanieldimons@gmail.com\", \"status\": \"M\",\"cert\": \"cert1\" }");

            String response = contract.createUser(ctx, "3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "cert1");
            assertThat(response).isEqualTo("");
        }

        @Test
        public void whenUserDoesNotExist() {
            UserContract contract = new UserContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("3324784V"))
                    .thenReturn("");

            String response = contract.createUser(ctx, "3324784V", "Mr", "Dan", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "cert1");
            assertThat(response).isEqualTo("{\"address\":\"52 Strand Street Skerries Dublin\",\"cert\":\"cert1\",\"dob\":\"01-01-1999\",\"email\":\"sanieldimons@gmail.com\",\"firstname\":\"Dan\",\"gender\":\"M\",\"identifier\":\"3324784V\",\"status\":\"M\",\"surname\":\"Simons\",\"title\":\"Mr\"}");
        }
    }

    @Nested
    class InvokeUpdateUserTransaction{
        @Test
        public void whenUserExists() {
            UserContract contract = new UserContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("3324784V"))
                    .thenReturn("{ \"identifier\": \"3324784V\", \"title\": \"Mr\", \"firstname\": \"Dan\", \"surname\": \"Simons\", \"address\": \"52 Strand Street Skerries Dublin\", \"dob\": \"01-01-1999\",\"gender\": \"M\", \"email\": \"sanieldimons@gmail.com\", \"status\": \"M\",\"cert\": \"cert1\" }");

            String response = contract.updateUser(ctx, "3324784V", "Mr", "Daniel(Dan)", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "cert1");
            System.out.println(response);
            assertThat(response).isEqualTo("{\"address\":\"52 Strand Street Skerries Dublin\",\"cert\":\"cert1\",\"dob\":\"01-01-1999\",\"email\":\"sanieldimons@gmail.com\",\"firstname\":\"Daniel(Dan)\",\"gender\":\"M\",\"identifier\":\"3324784V\",\"status\":\"M\",\"surname\":\"Simons\",\"title\":\"Mr\"}");
        }

        @Test
        public void whenUserDoesNotExist() {
            UserContract contract = new UserContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("3324784V"))
                    .thenReturn("");

            String response = contract.updateUser(ctx, "3324784V", "Mr", "Daniel(Dan)", "Simons", "52 Strand Street Skerries Dublin", "01-01-1999", "M", "sanieldimons@gmail.com", "M", "cert1");
            assertThat(response).isEqualTo("");
        }
    }



}
