export function extractUrl(homepage) {
  if (!homepage) return null;

  const aMatch = homepage.match(/href\s*=\s*"(https?:\/\/[^"]+)"/i);
  if (aMatch) {
    return aMatch[1];
  }

  const urlMatch = homepage.match(/(https?:\/\/\S+)/i);
  if (urlMatch) {
    return urlMatch[1].replace(/['"<>]/g, "");
  }

  return null;
}
