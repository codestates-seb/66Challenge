import axios from 'axios';
import { getCookie } from './cookies';
import { getAuthProps } from './moduleInterface';

export async function getHabitAuths({ habitId, page, size }: getAuthProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths?page=${page}&size=${size}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}