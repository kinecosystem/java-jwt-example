import io.jsonwebtoken.Jwts;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.openssl.PEMParser;

public class Main  {
    public static void main(String[] args) {
        String jwt = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImVzMjU2XzAifQ.eyJvZmZlcl9pZCI6ImEyNmUzZTQ1LTIxNjEtNDFjNy05NjE2LWIxNjNiMWUzMWViZiIsInBheW1lbnQiOnsiYmxvY2tjaGFpbiI6InN0ZWxsYXItdGVzdG5ldCIsInRyYW5zYWN0aW9uX2lkIjoiNjc3NjYzMzc1OTcxMTIzMyJ9LCJzZW5kZXJfdXNlcl9pZCI6ImRhbHRvbi50ZXN0MjFfMnhyIiwiaWF0IjoxNTI5MzUwOTA1LCJleHAiOjE1MzA5MDE4NTYyOTQsImlzcyI6ImtpbiIsInN1YiI6InBheW1lbnRfY29uZmlybWF0aW9uIn0.O8llNN0fOOH_X-Hy6SWqFvnaIhVAjjwy1PTEnsa4P71F1hgYfY03lLIK0dLzoIwB_JfYVttPjgYSxKQlOxbj6w";
        String publicKeyStr = "-----BEGIN PUBLIC KEY-----\nMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEM7Dbok9yzQEGZ5HYEw4huZ5OON5ZsGzj\n+SlIB31Ha2UWmq8s6v+W7xdmlhxPmXFj6MOxC2+rgHT/lITuB5lE+A==\n-----END PUBLIC KEY-----";

        if (args.length == 1) {
            jwt = args[0];
        }

        System.out.println( "jwt: " + jwt );
        System.out.println( "publicKey: " + publicKeyStr );
        try {
            InputStream stream = new ByteArrayInputStream(publicKeyStr.getBytes(StandardCharsets.UTF_8));
            byte[] pemBytes = new PEMParser(new InputStreamReader(stream)).readPemObject().getContent();

            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(pemBytes);
            PublicKey publicKey = kf.generatePublic(keySpec);

            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt);
            System.out.println( "its ok!" );
        } catch (Exception e) {
            System.out.println( "exception: " + e.toString() );
        }
    }
}
