import { Cookies } from 'react-cookie';

const cookies = new Cookies();
interface Ioption {
  path: string;
  secure?: boolean;
  httpOnly?: boolean;
}
export const setCookie = (
  name: string,
  value: string | string[],
  option: Ioption,
): void => {
  cookies.set(name, value, option);
};

export const getCookie = (name: string): string => {
  return cookies.get(name);
};

export const removeCookie = (name: string): void => {
  cookies.remove(name, { path: '/' });
};
