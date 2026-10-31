export type Status = 'Applied' | 'Interviewing' | 'Offer' | 'Rejected';
export type Priority = 'High' | 'Medium' | 'Low'; // <-- NEW

export interface Job {
  id: string;
  company: string;
  role: string;
  status: Status;
  priority: Priority; // <-- NEW
  dateAdded: string;
}