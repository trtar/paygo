export class AuthService {
  /* eslint-disable */

  loggedIn() {
    return !!localStorage.getItem('session');
  }
}
