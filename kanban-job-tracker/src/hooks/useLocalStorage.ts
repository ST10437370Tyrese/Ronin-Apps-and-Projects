import { useState, useEffect } from 'react';

export function useLocalStorage<T>(key: string, initialValue: T) {
  const [isLoading, setIsLoading] = useState(true);
  const [storedValue, setStoredValue] = useState<T>(initialValue);

  // Simulate a 1-second network request on load
  useEffect(() => {
    const timer = setTimeout(() => {
      try {
        const item = window.localStorage.getItem(key);
        if (item) {
          setStoredValue(JSON.parse(item));
        }
      } catch (error) {
        console.error("Failed to load local storage", error);
      } finally {
        setIsLoading(false);
      }
    }, 1000); // 1 second delay

    return () => clearTimeout(timer);
  }, [key]);

  // Save to local storage whenever state changes
  const setValue = (value: T | ((val: T) => T)) => {
    try {
      const valueToStore = value instanceof Function ? value(storedValue) : value;
      setStoredValue(valueToStore);
      window.localStorage.setItem(key, JSON.stringify(valueToStore));
    } catch (error) {
      console.error("Failed to save to local storage", error);
    }
  };

  return { storedValue, setValue, isLoading };
}