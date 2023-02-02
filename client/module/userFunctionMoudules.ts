import axios, { AxiosError } from 'axios';
import type {
  SignUpProps,
  UserGeneralProps,
  getUserCertificateProps,
  profileImageProps,
  UserInfoType,
} from './moduleInterface';

export async function postUserSignUp({
  email,
  username,
  password,
  age,
  gender,
}: SignUpProps) {
  try {
    const response = await axios
      .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/users`, {
        email,
        username,
        password,
        age,
        gender,
      })
      .then((res) => res.status);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}

export async function getUserEmailOverlapVerify(
  email: string,
): Promise<boolean> {
  try {
    const response: boolean = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/emails/check?email=${email}`,
      )
      .then((res) => res.data);
    console.log(response);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUsernameOverlapVerify(
  username: string,
): Promise<boolean> {
  try {
    const response: boolean = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/usernames/check?username=${username}`,
      )
      .then((res) => res.data);
    console.log(response);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserInfo({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`, {
        headers: {
          // Authorization: getCookie('accessJwtToken'),
        },
      })
      .then((res): UserInfoType => res.data);
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function deleteUser({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .delete(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`)
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchUserInfo({ userId, body }) {
  try {
    const response = await axios
      .patch(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`, body, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => res.status);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}

export async function patchOauth({ userId, data }) {
  try {
    const response = await axios
      .patch(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`, data, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => res.status);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}

export async function getUserCertificate({
  userId,
  habitId,
}: getUserCertificateProps) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/${habitId}/certificates`,
      {
        headers: {
          // Authorization: getCookie('accessJwtToken'),
        },
      },
    );
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserHabitsCategories({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/categories`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getUserHabitsHosts({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/hosts`)
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function getPasswordCheck({ userId }: UserGeneralProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/passwords/check`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function profileChange({
  userId,
  profileImage,
}: profileImageProps) {
  try {
    const response = await axios.patch(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`,
      {
        file: profileImage,
      },
      {
        headers: {
          // Authorization: getCookie('accessJwtToken'),
          'Content-Type': 'multipart/form-data',
        },
      },
    );
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function profileDelete({ userId }) {
  try {
    const response = await axios.delete(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/profiles`,
      {
        headers: {
          // Authorization: getCookie('accessJwtToken'),
        },
      },
    );
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function postUserEmailAuth(email: string) {
  try {
    const response = await axios
      .post(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/users/email-verification-requests?email=${email}`,
        null,
      )
      .then((res) => res.status);
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}
