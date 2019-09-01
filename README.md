# biometric_auth_rest_api

**T-Auth** is a typing biometrics authentication **API**.

A listener interface is implemented in a client application to record keystroke dynamics statistics about keys a user presses and turns them into typing patterns. And then it sends them to the authentication server which analyzes and verifies the recorded typing patterns against previous patterns from the real user.

The results (***Similarity Score***) are sent back to the client application to decide about authenticating the user.
It was an exciting project but I have enjoyed more the implementation of the authorization server using **OAuth2.0** standard protocol with **JWT**.
