import axios from 'axios';
import type { UserGeneralProps } from './moduleInterface';

export async function getUserBookmarks({ userId, cookie }: UserGeneralProps) {
  try {
    const response = await axios
      .get(`${process.env.NEXT_PUBLIC_SERVER_URL}/bookmarks/${userId}`, {
        headers: {
          Authorization: cookie,
        },
      })
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
