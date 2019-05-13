import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const login = LoginRequest => async dispatch => {
  try {
    // post => Login Request
    // extract token from res.data
    // store the token in the localStorage
    // set out token in header ***
    // decode token on React
    // dispatch to our security

    const res = await axios.post("/api/users/login", LoginRequest);
    const { token } = res.data;
    localStorage.setItem("jwtToken", token);
    setJWTToken(token);
    const decode = jwt_decode(token);
    dispatch({
      type: SET_CURRENT_USER,
      payload: decode
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
