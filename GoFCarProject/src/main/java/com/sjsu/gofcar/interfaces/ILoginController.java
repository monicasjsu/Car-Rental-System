package com.sjsu.gofcar.interfaces;

import com.sjsu.gofcar.model.user.AdminDetails;
import com.sjsu.gofcar.model.user.Login;
import com.sjsu.gofcar.model.user.UserDetails;

public interface ILoginController {
  UserDetails register(UserDetails userDetails);
  AdminDetails registerAdmin(AdminDetails adminDetails);
  String login(Login login);
  String loginAdmin(Login login);
}
